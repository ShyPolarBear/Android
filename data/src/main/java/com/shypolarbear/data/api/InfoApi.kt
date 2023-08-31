package com.shypolarbear.data.api

import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.model.more.ChangeInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface InfoApi {
    @GET("api/user/me")
    suspend fun getMyInfo(): Response<InfoResponse>

    @PUT("api/user/me")
    suspend fun changeMyInfo(
        @Body
        changeMyInfoRequest: ChangeInfoRequest,
    ): Response<InfoResponse>
}