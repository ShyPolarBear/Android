package com.shypolarbear.domain.usecase.quiz

import com.shypolarbear.domain.model.quiz.SubmitResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo

class QuizSubmitOXUseCase(
    private val repo: QuizRepo
) {
    suspend operator fun invoke(quizId: Int, answer: String): Result<SubmitResponse>{
        return repo.submitQuizOX(quizId, answer)
    }
}