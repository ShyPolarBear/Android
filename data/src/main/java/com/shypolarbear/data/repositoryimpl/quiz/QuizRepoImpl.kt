package com.shypolarbear.data.repositoryimpl.quiz

import com.shypolarbear.data.api.quiz.QuizApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.quiz.DailyQuizResponse
import com.shypolarbear.domain.model.quiz.ReviewQuizResponse
import com.shypolarbear.domain.model.quiz.SolvedStateResponse
import com.shypolarbear.domain.model.quiz.SubmitRequestMulti
import com.shypolarbear.domain.model.quiz.SubmitRequestOX
import com.shypolarbear.domain.model.quiz.SubmitResponse
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

    override suspend fun requestQuizSolvedState(): Result<SolvedStateResponse> {
        return try {
            val response = api.requestQuizSolvedState()
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

    override suspend fun requestQuizReview(): Result<ReviewQuizResponse> {
        return try {
            val response = api.requestReviewQuiz()
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

    override suspend fun submitQuizOX(quizId: Int, answer: SubmitRequestOX): Result<SubmitResponse> {
        return try {
            val response = api.submitQuizOX(quizId, answer)
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

    override suspend fun submitQuizMulti(quizId: Int, answer: SubmitRequestMulti): Result<SubmitResponse> {
        return try {
            val response = api.submitQuizMulti(quizId, answer)
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