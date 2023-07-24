package com.shypolarbear.domain.model.feed.feedDetail

data class ChildComment(
    val author: String,
    val authorProflieImage: String,
    val commentId: Int,
    val content: String,
    val createdDate: String,
    val isAuthor: Boolean,
    val isLike: Boolean,
    val likeCount: Int
)