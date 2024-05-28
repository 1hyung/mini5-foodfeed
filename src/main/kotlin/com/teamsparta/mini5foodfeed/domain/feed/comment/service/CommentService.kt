package com.teamsparta.mini5foodfeed.domain.feed.comment.service

import com.teamsparta.mini5foodfeed.domain.feed.comment.dto.CommentRequest
import com.teamsparta.mini5foodfeed.domain.feed.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.feed.comment.repository.CommentRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun createComment(feedId: Long, request: CommentRequest): CommentResponse {
        val comment = Comment(feedId = feedId, contents = request.contents)
        commentRepository.save(comment)
        return CommentResponse(contents = request.contents)
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