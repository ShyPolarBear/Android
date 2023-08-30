package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedChange.FeedChangeResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedChangeUseCase (
    private val repo: FeedRepo
) {
    suspend fun requestChangePost(
        feedId: Int,
        content: String,
        feedImages: List<String>?,
        title: String
    ): Result<FeedChangeResponse> {
        return repo.requestChangePostData(feedId, content, feedImages, title)
    }
}