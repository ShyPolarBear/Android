package com.shypolarbear.domain.repository.quiz

import com.shypolarbear.domain.model.quiz.DailyQuizResponse

interface QuizRepo {
    suspend fun requestQuiz(): Result<DailyQuizResponse>
}