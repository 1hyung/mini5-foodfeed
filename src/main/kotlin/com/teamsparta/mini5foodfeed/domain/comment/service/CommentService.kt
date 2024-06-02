package com.teamsparta.mini5foodfeed.domain.comment.service

import com.teamsparta.mini5foodfeed.common.exception.ModelNotFoundException
import com.teamsparta.mini5foodfeed.common.exception.NotAuthenticationException
import com.teamsparta.mini5foodfeed.common.status.OrderType
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentRequest
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.comment.model.toResponse
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.domain.like.repository.CommentLikeRepository
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository,
    private val commentLikeRepository: CommentLikeRepository,
) {

    fun createComment(feedId: Long, request: CommentRequest, userId: Long): CommentResponse {
        val feed = getValidatedFeed(feedId)
        val user: Users? = userRepository.findByIdOrNull(userId)
        return commentRepository.save(
            Comment(
                contents = request.contents,
                createdAt = LocalDateTime.now(),
                feed = feed,
                user = user,
                commentLike = null,
                likedCount = 0
            )
        ).toResponse()
    }

    fun updateComment(feedId: Long, commentId: Long, request: CommentRequest, userId: Long): CommentResponse {
        getValidatedFeed(feedId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if (userId != comment.user!!.id)  throw NotAuthenticationException("comment")
        comment.contents = request.contents
        commentRepository.save(comment)
        return CommentResponse(commentId = commentId,contents = request.contents, createdAt = LocalDateTime.now(), likedCount = comment.likedCount)
    }

    @Transactional
    fun deleteComment(feedId: Long, commentId: Long, userId: Long) {
        getValidatedFeed(feedId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        val commentLike = commentLikeRepository.findByComment(comment)
        if (userId != comment.user!!.id)  throw NotAuthenticationException("comment")
        commentRepository.delete(comment)
        if (commentLike != null) {
            commentLikeRepository.deleteAll(commentLike)
        }
    }

    fun getMyComments(userId: Long, order: OrderType, page: Int): List<CommentResponse> {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val pageRequest = PageRequest.of(page, 10)
        val comments = commentRepository.findByUserOrderByParam(user, order, pageRequest)
        return comments.map{it.toResponse()}
    }

    private fun getValidatedFeed (feedId: Long) : Feed {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        return feed
    }
}