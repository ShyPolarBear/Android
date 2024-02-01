package com.shypolarbear.domain.model.quiz

data class Quiz(
    val quizId: Int,
    val type: String,
    val question: String,
    val time: Int,
    val choices: List<Choice>?,
)

data class Choice(
    val id: Int,
    val text: String,
)
