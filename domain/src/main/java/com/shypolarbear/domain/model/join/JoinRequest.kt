package com.shypolarbear.domain.model.join

data class JoinRequest(
    val socialAccessToken: String,
    val nickName: String,
    val phoneNumber: String,
    val email: String,
    val profileImage: String
)
