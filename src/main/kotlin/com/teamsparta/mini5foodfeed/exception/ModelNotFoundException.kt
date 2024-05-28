package com.teamsparta.mini5foodfeed.exception

data class ModelNotFoundException(
    val model: String,
    val id: Long
) : RuntimeException("$model not found given id : $id")
