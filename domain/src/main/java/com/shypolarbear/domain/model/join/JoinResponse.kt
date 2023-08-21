package com.shypolarbear.domain.model.join

data class JoinResponse(
    val code: Number,
    val data: List<String>,
    val message: String
)

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
