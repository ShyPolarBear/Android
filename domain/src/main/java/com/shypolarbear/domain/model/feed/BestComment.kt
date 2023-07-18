package com.shypolarbear.domain.model.feed

data class BestComment(
    val author: String,
    val authorProfileImage: String,
    val commentId: Int,
    val content: String,
    val isAuthor: Boolean,
    val isLike: Boolean,
    val likeCount: Int
)