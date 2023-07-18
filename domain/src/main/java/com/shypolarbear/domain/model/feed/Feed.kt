package com.shypolarbear.domain.model.feed

data class Feed(
    val author: String,
    val authorProfileImage: String,
    val bestComment: BestComment,
    val commentCount: Int,
    val content: String,
    val createdDate: String,
    val feedId: Int,
    val feedImage: List<String>,
    val isAuthor: Boolean,
    val isLike: Boolean,
    val likeCount: String,
    val title: String
)