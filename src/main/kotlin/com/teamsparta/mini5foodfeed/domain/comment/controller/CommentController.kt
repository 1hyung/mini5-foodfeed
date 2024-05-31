package com.teamsparta.mini5foodfeed.domain.comment.controller

import com.teamsparta.mini5foodfeed.common.dto.CustomUser
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentRequest
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feeds/{feedId}/comments")
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping()
    fun createComment(
        @PathVariable feedId: Long,
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = commentService.createComment(feedId, request, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = commentService.updateComment(feedId, commentId, request, userId)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable feedId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Void> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        commentService.deleteComment(feedId, commentId, userId)
        return ResponseEntity.noContent().build()
    }
}