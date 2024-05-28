package com.teamsparta.mini5foodfeed.domain.feed.dto

data class CursorPage<Feed>(
    val nextCursor: Long?,
    val feed: List<Feed>
)
