package com.teamsparta.mini5foodfeed.domain.user.service

import com.teamsparta.mini5foodfeed.common.authority.JwtTokenProvider
import com.teamsparta.mini5foodfeed.common.authority.TokenInfo
import com.teamsparta.mini5foodfeed.common.exception.NotFoundException
import com.teamsparta.mini5foodfeed.common.status.ROLE
import com.teamsparta.mini5foodfeed.domain.user.dto.request.LoginRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import com.teamsparta.mini5foodfeed.domain.user.exception.UserIdIllegalStateException
import com.teamsparta.mini5foodfeed.domain.user.exception.UserIdNotFoundException
import com.teamsparta.mini5foodfeed.domain.user.model.UserRole
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import com.teamsparta.mini5foodfeed.domain.user.model.toResponse
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRoleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val userRoleRepository: UserRoleRepository,
    val authenticationManagerBuilder: AuthenticationManagerBuilder,
    val jwtTokenProvider: JwtTokenProvider
) {

    fun signUp(request: SignUpRequest): String {
        if (userRepository.existsByUserId(request.userId)) {
            throw UserIdIllegalStateException(request.userId)
        }
        val user = Users(
            userId = request.userId,
            userName = request.userName,
            password = request.password,
            commentLike = null,
            feedLike = null
        )
        userRepository.save(user)
        val userRole: UserRole = UserRole(null, ROLE.USER, user)
        userRoleRepository.save(userRole)
        return "회원가입이 완료 되었습니다."
    }

    fun logIn(request: LoginRequest): TokenInfo {

        if (!userRepository.existsByUserId(request.userId)) {
            throw UserIdNotFoundException(request.userId)
        }
        val authenticationToken = UsernamePasswordAuthenticationToken(request.userId, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtTokenProvider.createToken(authentication)
    }

    fun showUserInfo(id: Long): UserResponse {
        val user = userRepository.findByIdOrNull(id) ?: throw NotFoundException(id)
        return user.toResponse()
    }

    @Transactional
    fun updateUserProfile(id: Long, request: UpdateUserProfileRequest): String {
        val user = userRepository.findByIdOrNull(id) ?: throw NotFoundException(id)
        user.userName = request.userName
        return "수정 완료 되었습니다"
    }
}