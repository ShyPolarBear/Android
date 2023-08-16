package com.shypolarbear.data.api

import com.shypolarbear.domain.model.TokenRenew
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TokenApi {
    @POST("/api/auth/reissue")
    suspend fun renewToken(
        @Body
        refreshToken: String
    ): Response<TokenRenew>

}