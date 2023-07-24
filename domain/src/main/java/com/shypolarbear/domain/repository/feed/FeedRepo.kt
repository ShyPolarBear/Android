package com.shypolarbear.domain.repository.feed

import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedDetail.FeedCommentMock
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail

interface FeedRepo {
    suspend fun getFeedTotalData(): Result<FeedTotal>

    suspend fun getFeedDetailData(feedId: Int): Result<FeedDetail>

    suspend fun getFeedCommentData(feedId: Int): Result<FeedCommentMock>
}