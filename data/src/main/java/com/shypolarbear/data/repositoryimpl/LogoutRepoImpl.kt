package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.LogoutApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.more.LogoutResponse
import com.shypolarbear.domain.repository.LogoutRepo
import javax.inject.Inject

class LogoutRepoImpl @Inject constructor(
    private val api: LogoutApi
): LogoutRepo {
    override suspend fun requestLogoutData(): Result<LogoutResponse> {
        return try {
            val response = api.requestLogout()
            when {
                response.isSuccessful -> Result.success(response.body()!!)
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}