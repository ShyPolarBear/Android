package com.shypolarbear.data.repositoryimpl.quiz

import com.shypolarbear.data.api.quiz.QuizApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import com.shypolarbear.domain.repository.quiz.QuizRepo
import javax.inject.Inject

class QuizRepoImpl @Inject constructor(
    private val api: QuizApi
): QuizRepo {
    override suspend fun requestQuiz(): Result<DailyQuizResponse> {
        return try {
            val response = api.requestQuiz()
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}