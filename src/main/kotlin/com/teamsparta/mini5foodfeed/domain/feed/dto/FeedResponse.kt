package com.teamsparta.mini5foodfeed.domain.feed.dto


import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class FeedResponse(
    val id: Long?,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse>?,
    val tagVo: TagVo,
    val imageUrl : String
)
