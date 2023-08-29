package com.shypolarbear.domain.model.quiz

data class SolvedStateResponse(
    val code: Int,
    val data: SolvedData,
    val message: String,
)

data class SolvedData(
    val quizId: Int?,
    val isSolved: Boolean,
)
