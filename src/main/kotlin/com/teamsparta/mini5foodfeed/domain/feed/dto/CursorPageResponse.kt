package com.teamsparta.mini5foodfeed.domain.feed.dto

import org.springframework.data.domain.Slice

data class CursorPageResponse(
    val feedSlice: Slice<FeedResponse>,
    val nextCursor: Int?,
    )
