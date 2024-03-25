package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.SignupApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.signup.SignupRequest
import com.shypolarbear.domain.model.signup.SignupResponse
import com.shypolarbear.domain.repository.SignupRepo
import javax.inject.Inject

class SignupRepoImpl @Inject constructor(
    private val api: SignupApi,
) : SignupRepo {
    override suspend fun getSignupResponse(signupRequest: SignupRequest): Result<SignupResponse> {
        return try {
            val response = api.requestSignup(signupRequest)
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
