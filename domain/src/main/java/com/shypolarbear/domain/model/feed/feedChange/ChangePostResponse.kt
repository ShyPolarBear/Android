package com.shypolarbear.domain.model.feed.feedChange

data class ChangePostResponse(
    val code: Int,
    val data : FeedId,
    val message: String
)