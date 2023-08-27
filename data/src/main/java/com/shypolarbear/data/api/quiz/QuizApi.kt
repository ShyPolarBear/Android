package com.shypolarbear.data.api.quiz

import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {
    @GET("api/quiz/daily")
    fun requestQuiz() : Response<DailyQuizResponse>

}