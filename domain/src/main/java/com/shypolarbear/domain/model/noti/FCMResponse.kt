package com.shypolarbear.domain.model.noti

data class FCMResponse(
    val title: String,
    val body: String,
    val redirectTargetId: String,
    val type: String
)
