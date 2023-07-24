package com.shypolarbear.domain.model.feed.feedDetail

import com.shypolarbear.domain.model.feed.Comment

data class Data(
    val comments: List<Comment>,
    val isLast: Boolean
)