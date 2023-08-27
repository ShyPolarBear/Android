package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginResponse

interface LoginRepo {
    suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginResponse>
}