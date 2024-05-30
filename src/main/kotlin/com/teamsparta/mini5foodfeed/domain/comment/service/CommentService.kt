package com.teamsparta.mini5foodfeed.domain.comment.service

import com.teamsparta.mini5foodfeed.common.dto.CustomUser
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentRequest
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.comment.model.toResponse
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.common.exception.ModelNotFoundException
import com.teamsparta.mini5foodfeed.common.exception.NotAuthenticationException
import com.teamsparta.mini5foodfeed.domain.feed.model.Feed
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository,
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
            )
        ).toResponse()
    }

    fun updateComment(feedId: Long, commentId: Long, request: CommentRequest, userId: Long): CommentResponse {
        val feed = getValidatedFeed(feedId)
        if (userId != feed.user!!.id)  throw NotAuthenticationException("feed")
        val comment = commentRepository.findById(commentId)
            .orElseThrow { RuntimeException("Comment not found") }
        comment.contents = request.contents
        commentRepository.save(comment)
        return CommentResponse(contents = request.contents, createdAt = LocalDateTime.now())
    }

    fun deleteComment(feedId: Long, commentId: Long, userId: Long) {
        val feed = getValidatedFeed(feedId)
        if (userId != feed.user!!.id)  throw NotAuthenticationException("feed")
        val comment = commentRepository.findById(commentId)
            .orElseThrow { RuntimeException("Comment not found") }
        commentRepository.delete(comment)
    }

    private fun getValidatedFeed (feedId: Long) : Feed {
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException("feed", feedId)
        return feed
    }
}