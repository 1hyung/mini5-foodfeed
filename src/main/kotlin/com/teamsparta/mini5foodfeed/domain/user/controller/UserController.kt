package com.teamsparta.mini5foodfeed.domain.user.controller

import com.teamsparta.mini5foodfeed.domain.user.dto.request.LoginRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import com.teamsparta.mini5foodfeed.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUpRequest))
    }

    @PostMapping("/login")
    fun logIn(@RequestBody loginRequest: LoginRequest):ResponseEntity<UserResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.logIn(loginRequest))
    }

    @PutMapping("/users/{userId}")
    fun updateUserProfile(
        @PathVariable userId: Long,
        @RequestBody updateUserProfileRequest: UpdateUserProfileRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userId,updateUserProfileRequest))
    }
}