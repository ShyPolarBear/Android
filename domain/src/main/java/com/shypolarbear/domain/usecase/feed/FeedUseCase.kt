package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedUseCase(
    private val repo: FeedRepo
) {
    suspend fun loadFeedTotalData(): Result<FeedTotal> {
        return repo.getFeedTotalData()
    }

    suspend fun loadFeedDetailData(feedId: Int): Result<FeedDetail> {
        return repo.getFeedDetailData(feedId)
    }
}