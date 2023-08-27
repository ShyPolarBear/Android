package com.shypolarbear.domain.model.more

data class GetInfoResponse (
    val code: Int,
    val data: Info,
    val message: String
)