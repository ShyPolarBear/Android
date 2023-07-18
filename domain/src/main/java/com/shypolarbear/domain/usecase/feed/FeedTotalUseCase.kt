package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.repository.feed.FeedTotalRepo

class FeedTotalUseCase(
    private val repo: FeedTotalRepo
) {
    suspend fun loadFeedTotalData(): Result<FeedTotal> {
        return repo.getFeedTotalData()
    }
}