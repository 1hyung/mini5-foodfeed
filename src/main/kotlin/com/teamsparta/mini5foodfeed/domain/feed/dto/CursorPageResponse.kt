package com.teamsparta.mini5foodfeed.domain.feed.dto

data class CursorPageResponse(
    val feedSlice: List<FeedResponse>,
    val nextCursor: Int?,
    )
