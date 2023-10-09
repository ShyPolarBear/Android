package com.shypolarbear.domain.model.mypage

data class MyCommentResponse(
    val code: Int,
    val data: MyComment,
    val message: String
)

data class MyComment(
    val count: Int,
    val last: Boolean,
    val content: List<MyCommentFeed>
)

data class MyCommentFeed(
    val feedId: Int,
    val title: String,
    val feedImage: String? = null,
    val author: String,
    val authorProfileImage: String? = null,
    val commentId: Int
)
