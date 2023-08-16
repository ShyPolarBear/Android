package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.TokenRenew
import com.shypolarbear.domain.repository.TokenRepo

class TokenRenewUseCase (
    private val repo: TokenRepo
){
    suspend fun loadTokens(refreshToken: String): Result<TokenRenew> {
        return repo.renewTokens(refreshToken)
    }
}