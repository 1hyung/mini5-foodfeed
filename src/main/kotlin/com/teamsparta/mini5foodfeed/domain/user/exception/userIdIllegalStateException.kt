package com.teamsparta.mini5foodfeed.domain.user.exception

data class UserIdIllegalStateException(val userId:String):RuntimeException("already use in username:$userId")