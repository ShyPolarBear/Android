package com.shypolarbear.data.api.mypage

import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Query

interface MyPostApi {

    @GET("/api/user/feeds")
    suspend fun getMyPost(
        @Query("lastFeedId") lastFeedId: Int?,
        @Query("limit") limit: Int?
    ): Response<MyPostResponse>
}