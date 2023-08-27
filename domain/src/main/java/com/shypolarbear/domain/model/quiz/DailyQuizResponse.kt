package com.shypolarbear.domain.model.quiz

data class DailyQuizResponse(
    val code: Int,
    val data: List<Quiz>,
    val message: String
)


