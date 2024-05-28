package com.teamsparta.mini5foodfeed.domain.user.controller

import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        TODO()
    }

    @PutMapping("/users/{userId}/profile")
    fun updateUserProfile(@PathVariable userId: Long,
                          @RequestBody updateUserProfileRequest: UpdateUserProfileRequest
    ): ResponseEntity<UserResponse> {
        TODO()
    }
}