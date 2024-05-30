package com.teamsparta.mini5foodfeed.exception

import com.teamsparta.mini5foodfeed.domain.user.exception.UserIdIllegalStateException
import com.teamsparta.mini5foodfeed.domain.user.exception.UserIdNotFoundException
import com.teamsparta.mini5foodfeed.common.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

class UserExceptionHandler {
    @ExceptionHandler(UserIdIllegalStateException::class)
    fun handleUserIdNotExistException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = e.message))
    }

    @ExceptionHandler(UserIdNotFoundException::class)
    fun handleUserIdNotFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = e.message))
    }


}