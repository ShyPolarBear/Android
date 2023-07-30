package com.shypolarbear.domain.model.feed

import com.shypolarbear.domain.model.feed.feedDetail.ChildComment

data class Comment(
    val commentId: Int = 0,
    val author: String = "",
    val authorProfileImage: String = "",
    val content: String = "",
    val likeCount: Int = 0,
    val isAuthor: Boolean = false,
    val isLike: Boolean = false,
    val createdDate: String = "",
    val childComments: List<ChildComment> = listOf()
)