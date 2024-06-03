package com.teamsparta.mini5foodfeed.domain.feed.dto

import java.time.LocalDateTime

data class FeedWithoutCommentResponse(
    val id: Long?,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val tagVo: TagVo,
    val imageUrl : String?,
    val likedCount : Int
)
