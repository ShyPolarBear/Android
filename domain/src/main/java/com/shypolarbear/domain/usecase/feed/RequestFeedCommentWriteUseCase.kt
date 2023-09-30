package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.CommentWriteResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class RequestFeedCommentWriteUseCase (
    private val repo: FeedRepo
) {
    suspend operator fun invoke(
        feedId: Int,
        parentId: Int?,
        content: String
    ): Result<CommentWriteResponse> {
        return repo.requestWriteFeedCommentData(feedId, parentId, content)
    }
}