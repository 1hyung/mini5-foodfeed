package com.teamsparta.mini5foodfeed.domain.feed.comment.controller

import com.teamsparta.mini5foodfeed.domain.feed.comment.dto.CommentRequest
import com.teamsparta.mini5foodfeed.domain.feed.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.comment.service.CommentService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/feeds")
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping("/{feedId}/comments")
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody request: CommentRequest,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<CommentResponse> {
        val response = commentService.createComment(feedId, request)
        return ResponseEntity.status(201).body(response)
    }

    @PutMapping("/{feedId}/comments/{commentId}")
    fun updateComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        val response = commentService.updateComment(feedId, commentId, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{feedId}/comments/{commentId}")
    fun deleteComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        httpServletRequest: HttpServletRequest
    ): ResponseEntity<Void> {
        commentService.deleteComment(feedId, commentId)
        return ResponseEntity.noContent().build()
    }
}