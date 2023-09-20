package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.repository.feed.FeedRepo

class LoadCommentUseCase(
    private val repo: FeedRepo
) {
    suspend operator fun invoke(feedId: Int): Result<FeedComment> {
        return repo.getFeedCommentData(feedId)
    }
}