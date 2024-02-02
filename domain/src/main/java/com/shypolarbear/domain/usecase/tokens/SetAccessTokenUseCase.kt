package com.shypolarbear.domain.usecase.tokens

import com.shypolarbear.domain.repository.TokenRepo

class SetAccessTokenUseCase(
    private val repo: TokenRepo,
) {
    suspend operator fun invoke(accessToken: String) {
        return repo.setAccessToken(accessToken)
    }
}
