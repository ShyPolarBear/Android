package com.shypolarbear.domain.model.join

data class JoinResponse(
    val code: Int,
    val data: List<String>,
    val message: String
)

data class Token(
    val accessToken: String,
    val refreshToken: String,
)

data class AlreadyExists(
    val available: Boolean
)
