package com.shypolarbear.domain.model.join

data class JoinResponse(
    val accessToken: String,
    val refreshToken: String,
    val code: Number,
    val message: String
)
