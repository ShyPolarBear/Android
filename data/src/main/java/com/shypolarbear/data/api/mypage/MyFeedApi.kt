package com.shypolarbear.data.api.mypage

import com.shypolarbear.domain.model.mypage.MyCommentResponse
import com.shypolarbear.domain.model.mypage.MyPostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyFeedApi {

    @GET("/api/user/feeds")
    suspend fun getMyPost(
        @Query("lastFeedId") lastFeedId: Int?,
        @Query("limit") limit: Int?,
    ): Response<MyPostResponse>

    @GET("/api/user/comments/feeds")
    suspend fun getMyComment(
        @Query("lastCommentId") lastCommentId: Int?,
        @Query("limit") limit: Int?,
    ): Response<MyCommentResponse>
}
