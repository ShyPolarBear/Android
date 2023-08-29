package com.shypolarbear.domain.model.quiz

data class ReviewQuizResponse(
    val code: Int,
    val data: Review,
    val message: String
)

data class Review(
    val count: Int,
    val content: List<Quiz>
)
