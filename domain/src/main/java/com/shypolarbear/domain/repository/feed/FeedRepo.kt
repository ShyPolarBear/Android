package com.shypolarbear.domain.repository.feed

import com.shypolarbear.domain.model.feed.CommentWriteResponse
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedChange.FeedChangeResponse
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import com.shypolarbear.domain.model.feed.feedLike.FeedLikeResponse

interface FeedRepo {
    suspend fun getFeedTotalData(sort: String, lastFeedId: Int?): Result<FeedTotal>

    suspend fun getFeedDetailData(feedId: Int): Result<FeedDetail>

    suspend fun getFeedCommentData(feedId: Int): Result<FeedComment>

    suspend fun requestChangePostData(
        feedId: Int,
        content: String,
        feedImages: List<String>?,
        title: String
    ): Result<FeedChangeResponse>

    suspend fun deleteFeedData(feedId: Int): Result<FeedChangeResponse>

    suspend fun writeFeedData(
        title: String,
        content: String,
        feedImages: List<String>?
    ): Result<FeedChangeResponse>

    suspend fun requestLikeFeedData(feedId: Int): Result<FeedLikeResponse>

    suspend fun requestWriteFeedCommentData(
        feedId: Int,
        parentId: Int?,
        content: String
    ): Result<CommentWriteResponse>
}