package com.shypolarbear.domain.model.login

data class LoginRequest(
    val socialAccessToken: String,
    val socialType: String = "Kakao",
)
