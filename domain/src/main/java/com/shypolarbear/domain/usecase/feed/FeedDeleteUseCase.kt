package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedChange.FeedChangeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedDeleteUseCase(
    private val repo: FeedRepo
) {
    suspend operator fun invoke(feedId: Int): Result<FeedChangeResponse> {
        return repo.deleteFeedData(feedId)
    }
}