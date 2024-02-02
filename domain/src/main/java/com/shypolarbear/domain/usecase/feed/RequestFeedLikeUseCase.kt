package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedLike.FeedLikeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class RequestFeedLikeUseCase(
    private val repo: FeedRepo,
) {
    suspend operator fun invoke(feedId: Int): Result<FeedLikeResponse> {
        return repo.requestLikeFeedData(feedId)
    }
}
