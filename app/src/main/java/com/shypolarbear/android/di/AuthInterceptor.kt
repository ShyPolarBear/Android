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
    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO("TokenRepoImpl에서 토큰 가져오는 동작 구현되면 주석 해제하기")
//        var accessToken = accessTokenUseCase()
//        var refreshToken = refreshTokenUseCase()

        // 임시 정의
        var accessToken = "access token"
        var refreshToken = "refresh token"

//        val addedAccessTokenRequest = chain.request().newBuilder().addHeader("accessToken","$accessToken").build()
        val addedAccessTokenRequest = chain.request().newBuilder().addHeader("accessToken","testHeader").build()
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

        Timber.d("헤더에 잘 붙음?: $addedAccessTokenRequest")

        return response
    }
}