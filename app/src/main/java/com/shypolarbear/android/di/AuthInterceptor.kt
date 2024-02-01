package com.shypolarbear.android.di

import com.shypolarbear.domain.usecase.RequestTokenRenewUseCase
import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.GetRefreshTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetRefreshTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
    private val tokenRenewUseCase: RequestTokenRenewUseCase,
) : Interceptor {

    private lateinit var accessToken: String
    private lateinit var refreshToken: String

    override fun intercept(chain: Interceptor.Chain): Response {
        runBlocking {
            accessToken = getAccessTokenUseCase()
            refreshToken = getRefreshTokenUseCase()
        }

        val addedAccessTokenRequest = chain.request().addTokenIntoHeader(accessToken)
        val response = chain.proceed(addedAccessTokenRequest)

        when (response.code) {
            401 -> {
                runBlocking {
                    tokenRenewUseCase(refreshToken)
                        .onSuccess {
                            accessToken = it.data.accessToken
                            refreshToken = it.data.refreshToken

                            setAccessTokenUseCase(accessToken)
                            setRefreshTokenUseCase(refreshToken)
                        }
                        .onFailure {
                        }
                }

                response.close()
                return chain.proceed(chain.request().addTokenIntoHeader(accessToken))
            }
            else -> response
        }
        return response
    }

    private fun Request.addTokenIntoHeader(accessToken: String): Request {
        return this.newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
    }
}
