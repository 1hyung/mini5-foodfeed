package com.teamsparta.mini5foodfeed.domain.feed.dto

data class UpdateFeedRequest (
    val title : String,
    val description : String,
    val tagVo: TagVo
)