package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.TokenRenew

interface TokenRepo {
    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)

    suspend fun renewTokens(refreshToken: String): Result<TokenRenew>
}
