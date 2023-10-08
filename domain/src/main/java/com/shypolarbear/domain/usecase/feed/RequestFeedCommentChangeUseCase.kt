package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.CommentChangeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class RequestFeedCommentChangeUseCase (
    private val repo: FeedRepo
){
    suspend operator fun invoke(commentId: Int, content: String): Result<CommentChangeResponse> {
        return repo.changeFeedCommentData(commentId, content)
    }
}