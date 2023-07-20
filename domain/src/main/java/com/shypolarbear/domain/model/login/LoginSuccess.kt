package com.shypolarbear.domain.model.login

data class LoginSuccess(
    val data: Tokens
)

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)
