package com.shypolarbear.domain.usecase.quiz

import com.shypolarbear.domain.model.quiz.ReviewQuizResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo

class QuizReviewUseCase(
    private val repo: QuizRepo
) {
    suspend operator fun invoke(): Result<ReviewQuizResponse> {
        return repo.requestQuizReview()
    }
}