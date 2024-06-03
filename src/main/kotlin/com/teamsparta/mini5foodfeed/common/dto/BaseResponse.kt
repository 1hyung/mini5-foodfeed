package com.teamsparta.mini5foodfeed.common.dto

import com.teamsparta.mini5foodfeed.common.status.ResultCode

data class BaseResponse<T>(
    val result: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg
)