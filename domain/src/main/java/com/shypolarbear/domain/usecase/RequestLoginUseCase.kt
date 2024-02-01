package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginResponse
import com.shypolarbear.domain.repository.LoginRepo

class RequestLoginUseCase(private val repo: LoginRepo) {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<LoginResponse> {
        return repo.getLoginResponse(loginRequest)
    }
}
