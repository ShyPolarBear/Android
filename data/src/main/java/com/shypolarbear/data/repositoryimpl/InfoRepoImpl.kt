package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.InfoApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.more.GetInfoResponse
import com.shypolarbear.domain.repository.InfoRepo
import javax.inject.Inject

class InfoRepoImpl @Inject constructor(
    private val api: InfoApi
): InfoRepo {
    override suspend fun getMyInfo(): Result<GetInfoResponse> {
        return try {
            val response = api.getMyInfo()
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