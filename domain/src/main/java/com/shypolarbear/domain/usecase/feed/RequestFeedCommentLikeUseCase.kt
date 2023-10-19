package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.commentLike.CommentLikeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class RequestFeedCommentLikeUseCase(
    private val repo: FeedRepo
) {
    suspend operator fun invoke(commentId: Int): Result<CommentLikeResponse> {
        return repo.requestLikeFeedComment(commentId)
    }
}