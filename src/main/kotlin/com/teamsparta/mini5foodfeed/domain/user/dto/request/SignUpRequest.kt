package com.teamsparta.mini5foodfeed.domain.user.dto.request

import jakarta.validation.constraints.Size

data class SignUpRequest(

    @field:Size(min = 8, max = 15, message = "userId 는 5 ~ 20 이내여야 합니다")
    val userId: String,


    @field:Size(min = 8, max = 20, message = "password 는 8 ~ 20 자 이내여야 합니다")
    val password: String,


    @field:Size(min = 2, max = 15, message = "userName 은 2 ~ 15 자 이내여야 합니다")
    val userName: String
)
