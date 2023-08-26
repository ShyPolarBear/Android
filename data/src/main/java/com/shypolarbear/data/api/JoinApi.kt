package com.shypolarbear.data.api

import com.google.gson.annotations.SerializedName
import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.model.join.JoinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinApi {
    @POST("/api/auth/join")
    suspend fun requestJoin(
        @Body
        joinRequest: JoinRequest
    ): Response<JoinResponse>
}