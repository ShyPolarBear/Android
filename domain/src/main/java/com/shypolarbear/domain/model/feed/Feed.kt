package com.shypolarbear.domain.model.feed

data class Feed(
    val feedId: Int = 0,
    val title: String = "",
    val content: String = "",
    val likeCount: Int = 0,
    val feedImages: List<String> = listOf(),
    val author: String = "",
    val authorProfileImage: String = "",
    val createdDate: String = "",
    val isLike: Boolean = false,
    val isAuthor: Boolean = false,
    val commentCount: Int = 0,
    val comment: Comment = Comment(),
)
