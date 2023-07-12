package com.shypolarbear.domain.model.login

import java.util.Date

data class LoginToken(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: Date,
    val refreshTokenExp: Date
)
