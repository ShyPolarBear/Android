package com.shypolarbear.data.api.quiz

import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface QuizApi {
    @GET("/api/quiz/daily")
    suspend fun requestQuiz(): Response<DailyQuizResponse>

}