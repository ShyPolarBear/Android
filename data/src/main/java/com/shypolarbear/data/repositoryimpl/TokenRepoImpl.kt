package com.shypolarbear.data.repositoryimpl

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shypolarbear.data.api.TokenApi
import com.shypolarbear.data.repositoryimpl.TokenRepoImpl.PreferenceKeys.ACCESS_TOKEN
import com.shypolarbear.data.repositoryimpl.TokenRepoImpl.PreferenceKeys.REFRESH_TOKEN
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.TokenRenew
import com.shypolarbear.domain.repository.TokenRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class TokenRepoImpl @Inject constructor(
    private val api: TokenApi,
    @ApplicationContext private val context: Context
): TokenRepo {

    @Singleton
    val Context.tokenDataStore by preferencesDataStore("tokens")

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override suspend fun getAccessToken(): String {
        val userAccessToken: Flow<String?> = context.tokenDataStore.data.map {
            it[ACCESS_TOKEN]
        }
        return userAccessToken.first().toString()
    }

    override suspend fun getRefreshToken(): String {
        val userRefreshToken: Flow<String?> = context.tokenDataStore.data.map {
            it[REFRESH_TOKEN]
        }
        return userRefreshToken.first().toString()
    }
    override suspend fun setAccessToken(accessToken: String) {
        context.tokenDataStore.edit {prefs ->
            prefs[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        context.tokenDataStore.edit {prefs ->
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun renewTokens(refreshToken: String): Result<TokenRenew> {
        return try {
            val response = api.renewToken(refreshToken)
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