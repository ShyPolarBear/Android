package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginSuccess
import com.shypolarbear.domain.repository.LoginRepo

class LoginUseCase(private val repo: LoginRepo) {
    suspend fun postLogin(loginRequest: LoginRequest): Result<LoginSuccess> {
        return repo.getLoginResponse(loginRequest)
    }
}