package com.teamsparta.mini5foodfeed.exception

data class IllegalStateException(val userId:Long):RuntimeException("already in use id:$userId")