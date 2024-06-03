package com.teamsparta.mini5foodfeed.domain.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateUserProfileRequest(
    var id: Long?,
    @field:Size(min = 2, max = 15, message = "userName 은 2 ~ 15 자 이내여야 합니다")
    var userName: String
)
