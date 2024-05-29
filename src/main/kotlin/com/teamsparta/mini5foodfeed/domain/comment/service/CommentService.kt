package com.teamsparta.mini5foodfeed.domain.comment.service

import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentRequest
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.comment.model.toResponse
import com.teamsparta.mini5foodfeed.domain.comment.repository.CommentRepository
import com.teamsparta.mini5foodfeed.domain.feed.repository.FeedRepository
import com.teamsparta.mini5foodfeed.exception.ModelNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository
) {
    @Transactional
    fun createComment(feedId: Long, request: CommentRequest): CommentResponse {

//        val comment = Comment(feedId = feedId, contents = request.contents)
//        commentRepository.save(comment)
//        return CommentResponse(contents = request.contents)
        val feed = feedRepository.findByIdOrNull(feedId) ?: throw ModelNotFoundException ("feed" , feedId)
        return commentRepository.save(
            Comment(
                contents = request.contents,
                createdAt = LocalDateTime.now(),
                feed = feed
            )
        ).toResponse()
    }

    @Transactional
    fun updateComment(feedId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { RuntimeException("Comment not found") }
        comment.contents = request.contents
        commentRepository.save(comment)
        return CommentResponse(contents = request.contents)
    }

    @Transactional
    fun deleteComment(feedId: Long, commentId: Long) {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { RuntimeException("Comment not found") }
        commentRepository.delete(comment)
    }
}