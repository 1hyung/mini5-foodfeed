package com.teamsparta.mini5foodfeed.domain.user.controller

import com.teamsparta.mini5foodfeed.common.authority.TokenInfo
import com.teamsparta.mini5foodfeed.common.dto.BaseResponse
import com.teamsparta.mini5foodfeed.common.dto.CustomUser
import com.teamsparta.mini5foodfeed.domain.user.dto.request.LoginRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import com.teamsparta.mini5foodfeed.domain.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
    val userService: UserService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<BaseResponse<Unit>> {
        val resultMsg: String = userService.signUp(signUpRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(BaseResponse(message = resultMsg))
    }

    @PostMapping("/login")
    fun logIn(@RequestBody loginRequest: LoginRequest): ResponseEntity<BaseResponse<TokenInfo>> {
        val tokenInfo = userService.logIn(loginRequest)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(BaseResponse(data = tokenInfo))
    }

    //내 정보 보기
    @GetMapping("/info")
    fun searchMyInfo(): ResponseEntity<UserResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.showUserInfo(userId))
    }

    //내 정보 수정

    @PutMapping("/info")
    fun updateUserProfile(@RequestBody @Valid updateUserProfileRequest: UpdateUserProfileRequest): ResponseEntity<BaseResponse<Unit>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        updateUserProfileRequest.id = userId
        val resultMsg: String = userService.updateUserProfile(userId, updateUserProfileRequest)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(BaseResponse(message = resultMsg))
    }
}