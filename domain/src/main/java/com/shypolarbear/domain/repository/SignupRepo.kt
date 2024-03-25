package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.signup.SignupRequest
import com.shypolarbear.domain.model.signup.SignupResponse

interface SignupRepo {
    suspend fun getSignupResponse(signupRequest: SignupRequest): Result<SignupResponse>
}
