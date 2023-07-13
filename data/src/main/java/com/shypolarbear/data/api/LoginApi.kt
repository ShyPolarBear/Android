package com.shypolarbear.data.api

import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginSuccess
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/auth/login")
    suspend fun postLogin(
        @Body
        loginRequest: LoginRequest,
    ): Response<LoginSuccess>
}