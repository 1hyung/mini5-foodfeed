package com.teamsparta.mini5foodfeed.domain.user.exception

data class userIdIllegalStateException(val userId:String):RuntimeException("already use in username:$userId")