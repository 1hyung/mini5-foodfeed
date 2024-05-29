package com.teamsparta.mini5foodfeed.domain.user.exception

data class UserIdNotFoundException(val userId: String): RuntimeException("Not Found UserId: $userId")
