package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.more.LogoutResponse
import com.shypolarbear.domain.repository.LogoutRepo

class RequestLogoutUseCase(private val repo: LogoutRepo) {
    suspend operator fun invoke(): Result<LogoutResponse> {
        return repo.requestLogoutData()
    }
}
