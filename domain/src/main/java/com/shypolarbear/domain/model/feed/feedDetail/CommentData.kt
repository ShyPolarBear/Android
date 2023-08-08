package com.shypolarbear.domain.model.feed.feedDetail

import com.shypolarbear.domain.model.feed.Comment

data class CommentData(
    val comments: List<Comment>,
    val isLast: Boolean
)