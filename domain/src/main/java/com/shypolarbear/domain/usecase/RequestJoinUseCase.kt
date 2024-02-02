package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.model.join.JoinResponse
import com.shypolarbear.domain.repository.JoinRepo

class RequestJoinUseCase(
    private val repo: JoinRepo,
) {
    suspend operator fun invoke(joinRequest: JoinRequest): Result<JoinResponse> {
        return repo.getJoinResponse(joinRequest)
    }
}
