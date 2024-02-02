package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.JoinApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.model.join.JoinResponse
import com.shypolarbear.domain.repository.JoinRepo
import javax.inject.Inject

class JoinRepoImpl @Inject constructor(
    private val api: JoinApi,
) : JoinRepo {
    override suspend fun getJoinResponse(joinRequest: JoinRequest): Result<JoinResponse> {
        return try {
            val response = api.requestJoin(joinRequest)
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
