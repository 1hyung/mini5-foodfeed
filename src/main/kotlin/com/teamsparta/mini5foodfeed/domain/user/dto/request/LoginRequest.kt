package com.teamsparta.mini5foodfeed.domain.user.dto.request

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class LoginRequest(
    @get:Pattern(regexp = "^(?!\\s+$)(?!.*['\\\\]).+", message = "띄어쓰기와 ' 문자는 허용되지 않습니다.")
    @get:Size(min = 5, max = 20, message = "userId 는 5~20 이내여야 합니다")
    val userId: String,
    @get:Pattern(regexp = "^(?!\\s+$)(?!.*['\\\\]).+", message = "띄어쓰기와 ' 문자는 허용되지 않습니다.")
    @get:Size(min = 8, max = 20, message = "password 는 8~20 자 이내여야 합니다")
    val password: String
)
