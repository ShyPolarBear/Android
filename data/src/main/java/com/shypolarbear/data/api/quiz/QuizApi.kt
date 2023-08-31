package com.shypolarbear.data.api.quiz

import com.google.gson.annotations.SerializedName
import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import com.shypolarbear.domain.model.quiz.ReviewQuizResponse
import com.shypolarbear.domain.model.quiz.SolvedStateResponse
import com.shypolarbear.domain.model.quiz.SubmitResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizApi {
    @GET("/api/quiz/daily")
    suspend fun requestQuiz(): Response<DailyQuizResponse>

    @GET("/api/quiz/daily/whether-solved")
    suspend fun requestQuizSolvedState(): Response<SolvedStateResponse>

    @GET("/api/quiz/review")
    suspend fun requestReviewQuiz(): Response<ReviewQuizResponse>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/quiz/ox/{quizId}/score")
    suspend fun submitQuizOX(
        @Path("quizId") quizId: Int,
        @Body answer: String
    ): Response<SubmitResponse>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/quiz/multiple-choice/{quizId}/score")
    suspend fun submitQuizMulti(
        @Path("quizId") quizId: Int,
        @Body answer: Long
    ): Response<SubmitResponse>

}