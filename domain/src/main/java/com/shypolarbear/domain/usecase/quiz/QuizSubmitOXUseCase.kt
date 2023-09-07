package com.shypolarbear.domain.usecase.quiz

import com.shypolarbear.domain.model.quiz.SubmitRequestOX
import com.shypolarbear.domain.model.quiz.SubmitResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo

class QuizSubmitOXUseCase(
    private val repo: QuizRepo
) {
    suspend operator fun invoke(quizId: Int, answer: SubmitRequestOX): Result<SubmitResponse>{
        return repo.submitQuizOX(quizId, answer)
    }
}