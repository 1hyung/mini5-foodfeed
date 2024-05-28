package com.teamsparta.mini5foodfeed.domain.feed.dto

import com.teamsparta.mini5foodfeed.domain.user.entity.User
import java.time.LocalDateTime

data class FeedResponseDto(
    val id : Long?,
    val title : String,
    val description : String,
    val createdAt : LocalDateTime,
    val tags: List<String>?,
    val comments : List<Comments>?,
    val user : User
)
