package com.teamsparta.mini5foodfeed.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val contents: String,
    val createdAt: LocalDateTime,
)
