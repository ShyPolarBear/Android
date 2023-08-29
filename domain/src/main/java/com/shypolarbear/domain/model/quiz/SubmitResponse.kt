package com.shypolarbear.domain.model.quiz

data class SubmitResponse(
    val code: Int,
    val data: Correction,
    val message: String
)

data class Correction(
    val quizId: Int,
    val correctAnswer: String,
    val explanation: String,
    val isCorrect: Boolean,
    val point: Int
)

