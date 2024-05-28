package com.teamsparta.mini5foodfeed.exception

import com.teamsparta.mini5foodfeed.domain.user.exception.userIdIllegalStateException
import com.teamsparta.mini5foodfeed.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

class UserExceptionHandler{
    @ExceptionHandler(userNameIllegalStateException::class, userIdIllegalStateException::class)
    fun handleNotFoundException(e: Exception): ResponseEntity<ErrorResponse>{
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = e.message))
    }


}