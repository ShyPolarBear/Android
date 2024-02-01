package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.repository.feed.FeedRepo

class LoadFeedTotalUseCase(
    private val repo: FeedRepo,
) {
    suspend operator fun invoke(sort: String, lastFeedId: Int?): Result<FeedTotal> {
        return repo.getFeedTotalData(sort, lastFeedId)
    }
}
