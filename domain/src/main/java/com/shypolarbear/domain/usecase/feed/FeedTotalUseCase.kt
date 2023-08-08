package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedTotalUseCase(
    private val repo: FeedRepo
) {
    suspend fun loadFeedTotalData(): Result<FeedTotal> {
        return repo.getFeedTotalData()
    }
}