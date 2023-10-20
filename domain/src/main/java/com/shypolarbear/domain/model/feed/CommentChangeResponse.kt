package com.shypolarbear.domain.model.feed

data class CommentChangeResponse(
    val code: Int,
    val data: CommentId,
    val message: String
)