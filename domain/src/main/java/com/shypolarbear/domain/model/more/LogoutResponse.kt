package com.shypolarbear.domain.model.more

data class LogoutResponse(
    val code: Int,
    val data: Message,
    val message: String,
)

data class Message(
    val message: String,
)
