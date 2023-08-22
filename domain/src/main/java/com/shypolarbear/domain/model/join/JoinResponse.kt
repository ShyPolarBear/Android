package com.shypolarbear.domain.model.join

import com.shypolarbear.domain.model.Tokens

data class JoinResponse(
    val code: Int,
    val data: List<Tokens>,
    val message: String
)