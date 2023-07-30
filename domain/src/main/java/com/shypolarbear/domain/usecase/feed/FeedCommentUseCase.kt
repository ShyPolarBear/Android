package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedDetail.FeedCommentMock
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedCommentUseCase(
    private val repo: FeedRepo
) {
    suspend fun loadFeedCommentData(feedId: Int): Result<FeedCommentMock> {
        return repo.getFeedCommentData(feedId)
    }
}