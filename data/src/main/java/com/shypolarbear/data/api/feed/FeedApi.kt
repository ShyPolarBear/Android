package com.shypolarbear.data.api.feed

import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedDetail.FeedCommentMock
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FeedApi {
    @GET("api/feeds")
    suspend fun getFeedTotal(): Response<FeedTotal>

    @GET("api/feeds/{feedId}")
    suspend fun getFeedDetail(
        @Path("feedId", encoded = true) feedID: Int
    ): Response<FeedDetail>

    @GET("api/feeds/{feedId}/comment")
    suspend fun getFeedComment(
        @Path("feedId", encoded = true) feedID: Int
    ): Response<FeedCommentMock>
}