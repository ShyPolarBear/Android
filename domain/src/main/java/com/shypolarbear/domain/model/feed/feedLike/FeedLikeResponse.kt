package com.shypolarbear.domain.model.feed.feedLike

data class FeedLikeResponse(
    val code: Int,
    val data: Result,
    val message: String
)

data class Result(
    val result: String
)