package com.shypolarbear.android.di

import com.shypolarbear.domain.usecase.AccessTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val accessTokenUseCase: AccessTokenUseCase
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = accessTokenUseCase.loadAccessToken()
        val request = chain.request().newBuilder().addHeader("accessToken","$accessToken").build()

        return chain.proceed(request)
    }
}