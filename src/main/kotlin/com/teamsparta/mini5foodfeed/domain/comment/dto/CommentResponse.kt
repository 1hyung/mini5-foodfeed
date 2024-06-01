package com.teamsparta.mini5foodfeed.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val likedCount: Int,
)
