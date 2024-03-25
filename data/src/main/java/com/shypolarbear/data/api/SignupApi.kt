package com.shypolarbear.data.api

import com.shypolarbear.domain.model.signup.SignupRequest
import com.shypolarbear.domain.model.signup.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupApi {
    @POST("/api/auth/join")
    suspend fun requestSignup(
        @Body
        signupRequest: SignupRequest,
    ): Response<SignupResponse>
}
