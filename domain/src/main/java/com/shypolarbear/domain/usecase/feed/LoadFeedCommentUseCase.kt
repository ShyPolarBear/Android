package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.repository.feed.FeedRepo

class LoadFeedCommentUseCase(
    private val repo: FeedRepo
) {
    suspend operator fun invoke(feedId: Int, lastCommentId: Int?): Result<FeedComment> {
        return repo.getFeedCommentData(feedId, lastCommentId)
    }
}