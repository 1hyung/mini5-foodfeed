package com.teamsparta.mini5foodfeed.domain.feed.dto

data class TagRequest(
    val sweet: Boolean,
    val hot: Boolean,
    val spicy: Boolean,
    val cool: Boolean,
    val sweetMood: Boolean,
    val dateCourse: Boolean
)
