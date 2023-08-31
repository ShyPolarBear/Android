package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedChange.ChangePostResponse
import com.shypolarbear.domain.repository.feed.FeedRepo

class ChangePostUseCase (
    private val repo: FeedRepo
) {
    suspend fun requestChangePost(
        feedId: Int,
        content: String,
        feedImages: List<String>?,
        title: String
    ): Result<ChangePostResponse> {
        return repo.requestChangePost(feedId, content, feedImages, title)
    }
}