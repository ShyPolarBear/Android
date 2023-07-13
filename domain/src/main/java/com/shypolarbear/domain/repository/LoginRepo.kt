package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginSuccess

interface LoginRepo {
    suspend fun getLoginResponse(loginRequest: LoginRequest): Result<LoginSuccess>
}