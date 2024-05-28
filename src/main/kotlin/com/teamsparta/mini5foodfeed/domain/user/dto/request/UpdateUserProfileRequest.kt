package com.teamsparta.mini5foodfeed.domain.user.dto.request

data class UpdateUserProfileRequest(
    val name: String,
    val password: String //인증 인가 적용 시 불필요 할 것으로 예상
)
