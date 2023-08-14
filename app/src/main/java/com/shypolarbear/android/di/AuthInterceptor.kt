package com.shypolarbear.android.di

import com.shypolarbear.domain.usecase.AccessTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val accessTokenUseCase: AccessTokenUseCase
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO("TokenRepoImpl에서 토큰 가져오는 동작 구현되면 주석 해제하기")
//        val accessToken = accessTokenUseCase.loadAccessToken()
//        val request = chain.request().newBuilder().addHeader("accessToken","$accessToken").build()
        val request = chain.request().newBuilder().addHeader("accessToken","testHeader").build()

        Timber.d("헤더에 잘 붙음?: $request")
        return chain.proceed(request)
    }
}