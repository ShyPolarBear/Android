package com.shypolarbear.data.api

import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.model.more.ChangeInfoRequest
import com.shypolarbear.domain.model.more.CheckDuplicateNickNameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface InfoApi {
    @GET("api/user/me")
    suspend fun getMyInfo(): Response<InfoResponse>

    @PUT("api/user/me")
    suspend fun changeMyInfo(
        @Body
        changeMyInfoRequest: ChangeInfoRequest,
    ): Response<InfoResponse>

    @GET("api/user/duplicate-nickname")
    suspend fun requestCheckDuplicateNickName (
        @Query("nickName") nickName: String,
    ): Response<CheckDuplicateNickNameResponse>
}