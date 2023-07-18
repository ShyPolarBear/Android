package com.shypolarbear.data.api.feed

import com.shypolarbear.domain.model.feed.FeedTotal
import retrofit2.Response
import retrofit2.http.GET

interface FeedApi {
    @GET("api/feeds")
    suspend fun getFeedTotal(): Response<FeedTotal>
}