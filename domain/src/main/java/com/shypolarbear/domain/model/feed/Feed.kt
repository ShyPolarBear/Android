package com.shypolarbear.domain.model.feed

data class Feed(
    val feedId: Int,
    val title: String,
    val content: String,
    val likeCount: Int,
    val feedImage: List<String>,
    val author: String,
    val authorProfileImage: String,
    val createdDate: String,
    val isLike: Boolean,
    val isAuthor: Boolean,
    val commentCount: Int,
    val comment: Comment,
)