package com.shypolarbear.data.api

import com.shypolarbear.domain.model.TokenRenew
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {
    @POST("/api/auth/reissue")
    suspend fun renewToken(
        @Body
        refreshToken: String,
    ): Response<TokenRenew>
}
