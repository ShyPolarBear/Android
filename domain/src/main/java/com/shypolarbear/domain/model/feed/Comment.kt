package com.shypolarbear.domain.model.feed

data class Comment(
    val commentId: Int,
    val author: String,
    val authorProfileImage: String,
    val content: String,
    val likeCount: Int,
    val isAuthor: Boolean,
    val isLike: Boolean,
    val createdDate: String
)