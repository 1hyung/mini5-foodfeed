package com.teamsparta.mini5foodfeed.exception

data class userNameIllegalStateException(val userName:String):RuntimeException("already in use id:$userName")