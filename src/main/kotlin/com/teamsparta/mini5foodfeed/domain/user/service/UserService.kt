package com.teamsparta.mini5foodfeed.domain.user.service

import com.teamsparta.mini5foodfeed.domain.user.dto.request.LoginRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import com.teamsparta.mini5foodfeed.domain.user.exception.UserIdIllegalStateException
import com.teamsparta.mini5foodfeed.domain.user.exception.UserIdNotFoundException
import com.teamsparta.mini5foodfeed.domain.user.model.Users
import com.teamsparta.mini5foodfeed.domain.user.model.toResponse
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository
import com.teamsparta.mini5foodfeed.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class  UserService(
    val userRepository: UserRepository,
) {

    fun signUp(request: SignUpRequest): UserResponse {
        if(userRepository.existsByUserId(request.userId)){
            throw UserIdIllegalStateException(request.userId)
        }
        val user = Users(
            userId = request.userId,
            userName = request.userName,
            password = request.password
        )
        return userRepository.save(user).toResponse()
    }

    fun logIn(request: LoginRequest): UserResponse{
        if(!userRepository.existsByUserId(request.userId)){
            throw UserIdNotFoundException(request.userId)
        }
        val user = userRepository.findByUserId(request.userId)
        return user.toResponse()
    }

    @Transactional
    fun updateUserProfile(userId: Long, request: UpdateUserProfileRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException(userId)
        user.userName = request.userName
        return userRepository.save(user).toResponse()
    }
}