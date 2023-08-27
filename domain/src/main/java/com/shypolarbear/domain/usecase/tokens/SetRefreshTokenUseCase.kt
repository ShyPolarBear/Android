package com.shypolarbear.domain.usecase.tokens

import com.shypolarbear.domain.repository.TokenRepo

class SetRefreshTokenUseCase (
    private val repo: TokenRepo
){
    suspend operator fun invoke(refreshToken: String){
        return repo.setRefreshToken(refreshToken)
    }
}