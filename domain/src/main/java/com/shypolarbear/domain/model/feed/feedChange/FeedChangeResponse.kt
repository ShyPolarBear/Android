package com.shypolarbear.domain.model.feed.feedChange

data class FeedChangeResponse(
    val code: Int,
    val data : FeedId,
    val message: String
)