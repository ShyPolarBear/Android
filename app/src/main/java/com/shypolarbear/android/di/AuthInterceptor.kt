package com.shypolarbear.android.di

import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.GetRefreshTokenUseCase
import com.shypolarbear.domain.usecase.TokenRenewUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val accessTokenUseCase: GetAccessTokenUseCase,
    private val refreshTokenUseCase: GetRefreshTokenUseCase,
    private val tokenRenewUseCase: TokenRenewUseCase
): Interceptor {

    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO("TokenRepoImpl에서 토큰 가져오는 동작 구현되면 주석 해제하기")

        runBlocking {
            accessToken = accessTokenUseCase()
            refreshToken = refreshTokenUseCase()
        }

        val addedAccessTokenRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
        val response = chain.proceed(addedAccessTokenRequest)

        when (response.code) {
            401 -> {
                // Token 갱신하는 동작
                runBlocking {
                    val renewResponse = tokenRenewUseCase(refreshToken)

                    renewResponse
                        .onSuccess {
                            accessToken = it.data.accessToken
                            refreshToken = it.data.refreshToken

                            Timber.d("access token: $accessToken, refresh token: $refreshToken")
                        }
                        .onFailure {

                        }
                }

            }
            else -> response
        }

        return response
    }
}