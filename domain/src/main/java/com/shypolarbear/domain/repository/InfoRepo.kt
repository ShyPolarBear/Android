package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.more.InfoResponse

interface InfoRepo {
    suspend fun getMyInfo(): Result<InfoResponse>

    suspend fun changeMyInfo(
        nickName: String,
        profileImage: String?,
        email: String,
        phoneNumber: String
    ): Result<InfoResponse>
}