package com.shypolarbear.domain.usecase.quiz

import com.shypolarbear.domain.model.quiz.SubmitResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo

class QuizSubmitMultiUseCase(
    private val repo: QuizRepo
) {
    suspend operator fun invoke(quizId: Int, answer: Long): Result<SubmitResponse>{
        return repo.submitQuizMulti(quizId, answer)
    }
}