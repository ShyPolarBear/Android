package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.CommentChangeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class RequestFeedCommentDeleteUseCase(
    private val repo: FeedRepo
) {
    suspend operator fun invoke(commentId: Int): Result<CommentChangeResponse> {
        return repo.deleteFeedCommentData(commentId)
    }
}