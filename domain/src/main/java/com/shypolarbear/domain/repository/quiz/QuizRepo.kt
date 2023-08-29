package com.shypolarbear.domain.repository.quiz

import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import com.shypolarbear.domain.model.quiz.ReviewQuizResponse
import com.shypolarbear.domain.model.quiz.SolvedStateResponse
import com.shypolarbear.domain.model.quiz.SubmitResponse

interface QuizRepo {
    suspend fun requestQuiz(): Result<DailyQuizResponse>
    suspend fun requestQuizSolvedState(): Result<SolvedStateResponse>
    suspend fun requestQuizReview(): Result<ReviewQuizResponse>

    suspend fun submitQuizOX(quizId: Int, answer: String): Result<SubmitResponse>
    suspend fun submitQuizMulti(quizId: Int, answer: String): Result<SubmitResponse>
}