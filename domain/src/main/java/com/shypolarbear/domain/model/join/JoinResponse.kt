package com.shypolarbear.domain.model.join

import com.shypolarbear.domain.model.Tokens

data class JoinResponse(
    val code: Int,
    val data: Tokens,
    val message: String
)