package com.shypolarbear.domain.model.signup

import com.shypolarbear.domain.model.Tokens

data class SignupResponse(
    val code: Int,
    val data: Tokens,
    val message: String,
)
