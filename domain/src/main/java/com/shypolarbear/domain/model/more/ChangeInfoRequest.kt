package com.shypolarbear.domain.model.more

data class ChangeInfoRequest (
    val email: String,
    val nickName: String,
    val phoneNumber: String,
    val profileImage: String? = null
)