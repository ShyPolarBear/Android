package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.model.join.JoinResponse
import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.model.login.LoginResponse
import com.shypolarbear.domain.repository.LoginRepo

class LoginUseCase(private val repo: LoginRepo) {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<LoginResponse> {
        return repo.getLoginResponse(loginRequest)
    }
}