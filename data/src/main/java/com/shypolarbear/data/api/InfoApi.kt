package com.shypolarbear.data.api

import com.shypolarbear.domain.model.more.GetInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface InfoApi {
    @GET("api/user/me")
    suspend fun getMyInfo(): Response<GetInfoResponse>
}