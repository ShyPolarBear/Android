package com.shypolarbear.domain.model.feed

data class CommentWriteRequest(
    val parentId: Int?,
    val content: String,
)
