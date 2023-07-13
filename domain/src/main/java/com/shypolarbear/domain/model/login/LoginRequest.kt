package com.shypolarbear.domain.model.login

data class LoginRequest(
    val accessToken: String,
    val socialType: String = "KAKAO"
)
