package com.shypolarbear.domain.model.feed.feedChange

data class WriteFeedForm(
    val content: String,
    val feedId: Int,
    val feedImages: List<String>?,
    val title: String
)