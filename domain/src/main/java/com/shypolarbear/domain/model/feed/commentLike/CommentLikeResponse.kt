package com.shypolarbear.domain.model.feed.commentLike

data class CommentLikeResponse(
    val code: Int,
    val data: IsLiked,
    val message: String
)