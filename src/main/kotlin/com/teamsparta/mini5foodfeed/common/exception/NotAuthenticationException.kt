package com.teamsparta.mini5foodfeed.common.exception

data class NotAuthenticationException(
    val model: String
) : RuntimeException("not-authentication for model: $model")
