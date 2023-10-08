package com.shypolarbear.data.api.feed

import com.shypolarbear.domain.model.feed.CommentWriteRequest
import com.shypolarbear.domain.model.feed.CommentChangeResponse
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.commentLike.CommentLikeResponse
import com.shypolarbear.domain.model.feed.feedChange.FeedChangeResponse
import com.shypolarbear.domain.model.feed.feedChange.WriteFeedForm
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import com.shypolarbear.domain.model.feed.feedLike.FeedLikeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET("api/feeds")
    suspend fun getFeedTotal(
        @Query("sort") sort: String,
        @Query("lastFeedId") lastFeedId: Int?
    ): Response<FeedTotal>

    @GET("api/feeds/{feedId}")
    suspend fun getFeedDetail(
        @Path("feedId") feedID: Int
    ): Response<FeedDetail>

    @PUT("api/feeds/{feedId}")
    suspend fun requestChangePost(
        @Path("feedId") feedID: Int,
        @Body
        writeFeedForm: WriteFeedForm
    ): Response<FeedChangeResponse>

    @GET("api/comments/{feedId}")
    suspend fun getFeedComment(
        @Path("feedId") feedID: Int
    ): Response<FeedComment>

    @DELETE("api/feeds/{feedId}")
    suspend fun deleteFeed(
        @Path("feedId") feedID: Int
    ): Response<FeedChangeResponse>

    @POST("api/feeds")
    suspend fun writeFeed(
        @Body
        writeFeedForm: WriteFeedForm
    ): Response<FeedChangeResponse>

    @PUT("api/feeds/{feedId}/like")
    suspend fun likeFeed(
        @Path("feedId") feedID: Int
    ): Response<FeedLikeResponse>

    @POST("api/comments/{feedId}")
    suspend fun writeFeedComment(
        @Path("feedId") feedID: Int,
        @Body
        commentWriteRequest: CommentWriteRequest
    ): Response<CommentChangeResponse>

    @PUT("api/comments/{commentId}/like")
    suspend fun likeComment(
        @Path("commentId") commentID: Int
    ): Response<CommentLikeResponse>

    @DELETE("api/comments/{commentId}")
    suspend fun deleteFeedComment(
        @Path("commentId") commentID: Int
    ): Response<CommentChangeResponse>

    @PUT("api/comments/{commentId}")
    suspend fun changeFeedComment(
        @Path("commentId") commentID: Int,
        @Body
        content: String
    ): Response<CommentChangeResponse>
}