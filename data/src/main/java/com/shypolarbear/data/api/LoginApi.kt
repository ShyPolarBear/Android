package com.shypolarbear.data.api

import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/auth/login")
    suspend fun postLogin(
        @Body
        loginRequest: LoginRequest,
    ): Response<LoginResponse>
}