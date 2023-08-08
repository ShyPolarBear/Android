package com.shypolarbear.domain.model.feed.feedDetail

data class ChildComment(
    val author: String = "",
    val authorProfileImage: String = "",
    val commentId: Int = 0,
    val content: String = "",
    val createdDate: String = "",
    val isAuthor: Boolean = false,
    val isLike: Boolean = false,
    val likeCount: Int = 0
)