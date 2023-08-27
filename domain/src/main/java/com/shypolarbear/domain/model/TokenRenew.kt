package com.shypolarbear.domain.model

data class TokenRenew (
    val code: Int,
    val data: Tokens,
    val message: String
)