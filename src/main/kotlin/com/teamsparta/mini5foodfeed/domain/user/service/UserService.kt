package com.teamsparta.mini5foodfeed.domain.user.service

import com.teamsparta.mini5foodfeed.domain.user.dto.request.SignUpRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.request.UpdateUserProfileRequest
import com.teamsparta.mini5foodfeed.domain.user.dto.response.UserResponse
import com.teamsparta.mini5foodfeed.domain.user.exception.userIdIllegalStateException
import com.teamsparta.mini5foodfeed.domain.user.model.User
import com.teamsparta.mini5foodfeed.domain.user.model.toResponse
import com.teamsparta.mini5foodfeed.domain.user.repository.UserRepository
import com.teamsparta.mini5foodfeed.exception.NotFoundException
import com.teamsparta.mini5foodfeed.exception.userNameIllegalStateException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
) {

    fun signUp(request: SignUpRequest): UserResponse {
        // TODO: userId이 중복되는지 확인, 중복된다면 throw IllegalStateException
        if(userRepository.existsByUserId(request.userId)){
            throw userIdIllegalStateException(request.userId)
        }else if(userRepository.existsByUserName(request.userName)){
            throw userNameIllegalStateException(request.userName)
        }
        val user = User(
            userId = request.userId,
            userName = request.userName,
            password = request.password
        )
        return userRepository.save(user).toResponse()
        // TODO: request를 User로 변환 후 DB에 저장, 비밀번호는 저장시 암호화
    }

    @Transactional
    fun updateUserProfile(userId: Long, request: UpdateUserProfileRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException(userId)
        user.userName = request.userName
        return userRepository.save(user).toResponse()
        // TODO: 만약 userId에 해당하는 User가 없다면 throw ModelNotFoundException
        // TODO: DB에서 userId에 해당하는 User를 가져와서 updateUserProfileRequest로 업데이트 후 DB에 저장, 결과를 UserResponse로 변환 후 반환
    }
}