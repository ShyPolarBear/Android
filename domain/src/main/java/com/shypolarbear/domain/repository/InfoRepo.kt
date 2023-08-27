package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.more.GetInfoResponse

interface InfoRepo {
    suspend fun getMyInfo(): Result<GetInfoResponse>

//    suspend fun changeMyInfo(): Result<FeedTotal>
}