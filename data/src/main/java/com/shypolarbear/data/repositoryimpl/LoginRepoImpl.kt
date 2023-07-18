package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.LoginApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginSuccess
import com.shypolarbear.domain.repository.LoginRepo
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    private val api: LoginApi,
) : LoginRepo {
    override suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginSuccess> {
        return try {
            val response = api.postLogin(loginRequest)
            when {
                response.isSuccessful -> Result.success(api.postLogin(loginRequest).body()!!)
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}