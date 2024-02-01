package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.CommentChangeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class RequestFeedCommentWriteUseCase(
    private val repo: FeedRepo,
) {
    suspend operator fun invoke(
        feedId: Int,
        parentId: Int?,
        content: String,
    ): Result<CommentChangeResponse> {
        return repo.requestWriteFeedCommentData(feedId, parentId, content)
    }
}
