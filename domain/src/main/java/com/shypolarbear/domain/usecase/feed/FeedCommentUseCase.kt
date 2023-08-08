package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedCommentUseCase(
    private val repo: FeedRepo
) {
    suspend fun loadFeedCommentData(feedId: Int): Result<FeedComment> {
        return repo.getFeedCommentData(feedId)
    }
}