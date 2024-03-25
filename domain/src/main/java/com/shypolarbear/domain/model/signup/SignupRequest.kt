package com.shypolarbear.domain.model.signup

data class SignupRequest(
    val socialAccessToken: String,
    val nickName: String,
    val phoneNumber: String,
    val email: String,
    val profileImage: String,
)
