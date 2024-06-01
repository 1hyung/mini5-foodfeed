package com.teamsparta.mini5foodfeed.domain.feed.dto

import jakarta.validation.constraints.Size

data class UpdateFeedRequest(
    @Size(min = 1 , max = 100, message = "제목은 1 ~ 100 자 이내여야 합니다")
    val title: String,
    @Size(min = 1 , max = 2000, message = "본문은 1 ~ 2000 자 이내여야 합니다")
    val description: String,
    val tagVo: TagVo,
    val imageUrl: String,
)