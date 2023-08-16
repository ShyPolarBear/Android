package com.shypolarbear.domain.model

import com.shypolarbear.domain.model.login.Tokens

data class TokenRenew (
    val code: Int,
    val data: Tokens,
    val message: String
)