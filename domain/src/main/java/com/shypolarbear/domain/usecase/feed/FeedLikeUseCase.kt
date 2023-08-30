package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedLike.FeedLikeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedLikeUseCase (
    private val repo: FeedRepo
) {
    suspend fun requestLikeFeed(feedId: Int): Result<FeedLikeResponse> {
        return repo.requestLikeFeedData(feedId)
    }
}