package com.teamsparta.mini5foodfeed.common.exception

data class NotFoundException(val id: Long) : RuntimeException(" Not Found with give id:$id")