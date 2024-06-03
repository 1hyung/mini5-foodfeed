package com.teamsparta.mini5foodfeed.domain.comment.dto

import jakarta.validation.constraints.Size


data class CommentRequest(
    @field:Size(min = 1, max = 100, message = "댓글은 1 ~ 100 자 이내여야 합니다")
    val contents: String,
)