package com.teamsparta.mini5foodfeed.domain.feed.dto

data class CursorRequest(
    val cursorId: Long,
    val tags: List<String>
)
