package com.teamsparta.mini5foodfeed.domain.feed.dto


import com.teamsparta.mini5foodfeed.domain.comment.model.Comment
import com.teamsparta.mini5foodfeed.domain.user.model.User
import java.time.LocalDateTime

data class FeedResponse(
    val id: Long?,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val comments: List<Comment>?,
    val user: User
)
