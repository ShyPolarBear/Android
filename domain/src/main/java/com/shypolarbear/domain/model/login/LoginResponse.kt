package com.shypolarbear.domain.model.login

import com.shypolarbear.domain.model.Tokens

data class LoginResponse(
    val code: Int,
    val data: Tokens,
    val messages: String
)
