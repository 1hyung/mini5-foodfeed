package com.teamsparta.mini5foodfeed.domain.user.dto.request

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:Size(min = 8, max = 15, message = "userId 는 5 ~ 20 이내여야 합니다")
    val userId: String,

    @field:Size(min = 8, max = 15, message = "userId 는 5 ~ 20 이내여야 합니다")
    val password: String
)
