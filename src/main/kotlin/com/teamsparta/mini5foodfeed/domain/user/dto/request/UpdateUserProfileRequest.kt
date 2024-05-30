package com.teamsparta.mini5foodfeed.domain.user.dto.request

import jakarta.validation.constraints.NotBlank

data class UpdateUserProfileRequest(
    var id: Long?,
    @field:NotBlank
    var userName: String
)
