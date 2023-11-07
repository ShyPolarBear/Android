package com.shypolarbear.data.api

import com.shypolarbear.domain.model.more.LogoutResponse
import retrofit2.Response
import retrofit2.http.POST

interface LogoutApi {

    @POST("api/auth/logout")
    suspend fun requestLogout(): Response<LogoutResponse>

}