package com.shypolarbear.domain.model.more

data class CheckDuplicateNickNameResponse(
    val code: Int,
    val data: Available,
    val message: String
)

data class Available(
    val available: Boolean
)