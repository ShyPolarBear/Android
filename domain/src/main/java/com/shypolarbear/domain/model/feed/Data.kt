package com.shypolarbear.domain.model.feed

data class Data(
    val count: Int,
    val content: List<Feed>,
    val last: Boolean,
)
