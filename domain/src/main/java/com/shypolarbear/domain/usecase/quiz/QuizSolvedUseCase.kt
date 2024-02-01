package com.shypolarbear.domain.usecase.quiz

import com.shypolarbear.domain.model.quiz.SolvedStateResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo

class QuizSolvedUseCase(
    private val repo: QuizRepo,
) {
    suspend operator fun invoke(): Result<SolvedStateResponse> {
        return repo.requestQuizSolvedState()
    }
}
