package com.shypolarbear.domain.model.feed

data class Data(
    val feedCount: Int,
    val feeds: List<Feed>,
    val isLast: Boolean
)