package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.TokenApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.TokenRenew
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.repository.TokenRepo
import javax.inject.Inject

class TokenRepoImpl @Inject constructor(
    private val api: TokenApi
): TokenRepo {
    override fun getAccessToken(): String {
        TODO("DataStore에서 Access Token 가져오는 동작")
    }

    override fun getRefreshToken(): String {
        TODO("DataStore에서 Refresh Token 가져오는 동작")
    }

    override suspend fun renewTokens(): Result<TokenRenew> {
        return try {
            val response = api.renewToken()
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