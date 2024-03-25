package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.signup.SignupRequest
import com.shypolarbear.domain.model.signup.SignupResponse
import com.shypolarbear.domain.repository.SignupRepo

class RequestSignupUseCase(
    private val repo: SignupRepo,
) {
    suspend operator fun invoke(joinRequest: SignupRequest): Result<SignupResponse> {
        return repo.getSignupResponse(joinRequest)
    }
}
