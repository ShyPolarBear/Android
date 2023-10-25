package com.shypolarbear.android.di

import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.GetRefreshTokenUseCase
import com.shypolarbear.domain.usecase.RequestTokenRenewUseCase
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
    private val tokenRenewUseCase: RequestTokenRenewUseCase
): Interceptor {

    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    override fun intercept(chain: Interceptor.Chain): Response {

        runBlocking {
            accessToken = "zzz"
//            accessToken = accessTokenUseCase()
            refreshToken = refreshTokenUseCase()
        }

        Timber.d("access token: $accessToken, refresh token: $refreshToken")

        val addedAccessTokenRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
        val response = chain.proceed(addedAccessTokenRequest)

        when (response.code) {
            200 -> {
                Timber.d("유효한 토큰: $accessToken")
            }
            401 -> {
                Timber.d("갱신 전 access token: $accessToken, refresh token: $refreshToken")
                // Token 갱신하는 동작
                runBlocking {
                    tokenRenewUseCase(refreshToken)
                        .onSuccess {
                            accessToken = it.data.accessToken
                            refreshToken = it.data.refreshToken

                            Timber.d("갱신 후 access token: $accessToken, refresh token: $refreshToken")


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