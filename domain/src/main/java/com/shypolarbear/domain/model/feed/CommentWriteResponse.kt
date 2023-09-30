package com.shypolarbear.domain.model.feed

data class CommentWriteResponse(
    val code: Int,
    val data: CommentData,
    val message: String
)