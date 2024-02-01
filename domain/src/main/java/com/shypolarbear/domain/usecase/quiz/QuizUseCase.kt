package com.shypolarbear.domain.usecase.quiz

import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo

class QuizUseCase(
    private val repo: QuizRepo,
) {
    suspend operator fun invoke(): Result<DailyQuizResponse> {
        return repo.requestQuiz()
    }
}
