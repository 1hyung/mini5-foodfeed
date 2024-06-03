package com.teamsparta.mini5foodfeed.domain.user.controller

import com.teamsparta.mini5foodfeed.common.authority.TokenInfo
import com.teamsparta.mini5foodfeed.common.dto.BaseResponse
import com.teamsparta.mini5foodfeed.common.dto.CustomUser
import com.teamsparta.mini5foodfeed.common.status.OrderType
import com.teamsparta.mini5foodfeed.domain.comment.dto.CommentResponse
import com.teamsparta.mini5foodfeed.domain.comment.service.CommentService
import com.teamsparta.mini5foodfeed.domain.feed.dto.FeedWithoutCommentResponse
import com.teamsparta.mini5foodfeed.domain.feed.service.FeedService
import com.teamsparta.mini5foodfeed.domain.user.dto.request.LoginRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import com.teamsparta.mini5foodfeed.domain.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
    val userService: UserService,
    private val feedService: FeedService,
    private val commentService: CommentService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid signUpRequest: SignUpRequest): ResponseEntity<BaseResponse<Unit>> {
        val resultMsg: String = userService.signUp(signUpRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(BaseResponse(message = resultMsg))
    }

    @PostMapping("/login")
    fun logIn(@RequestBody @Valid loginRequest: LoginRequest): ResponseEntity<BaseResponse<TokenInfo>> {
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


    @GetMapping("/myFeeds")
    @PreAuthorize("hasRole('USER')")
    fun getMyFeed(
        @RequestParam order : OrderType,
        @RequestParam (defaultValue = "0")page : Int
    ) : ResponseEntity<List<FeedWithoutCommentResponse>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(feedService.getMyFeeds(userId, order, page))
    }

    @GetMapping("/myComments")
    @PreAuthorize("hasRole('USER')")
    fun getMyComments(
        @RequestParam order : OrderType,
        @RequestParam (defaultValue = "0") page : Int
    ) : ResponseEntity<List<CommentResponse>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getMyComments(userId, order, page))
    }
}