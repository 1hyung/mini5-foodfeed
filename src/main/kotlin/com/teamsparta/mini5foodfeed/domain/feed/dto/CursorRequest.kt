package com.teamsparta.mini5foodfeed.domain.feed.dto

data class CursorRequest(
    val cursor: Long,
    val size: Int = 10
)
