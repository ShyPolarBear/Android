package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.TokenRenew

interface TokenRepo {
    fun getAccessToken(): String

    fun getRefreshToken(): String

    suspend fun renewTokens(refreshToken: String): Result<TokenRenew>
}