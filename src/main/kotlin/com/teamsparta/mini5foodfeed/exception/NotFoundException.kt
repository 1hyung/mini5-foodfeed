package com.teamsparta.mini5foodfeed.exception

data class NotFoundException(val id:Long):RuntimeException(" Not Found with give id:$id")