package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.repository.TokenRepo

class RefreshTokenUseCase (
    private val repo: TokenRepo
){

    suspend fun setRefreshToken(refreshToken: String){
        repo.setRefreshToken(refreshToken)
    }
    suspend operator fun invoke(): String {
        return repo.getRefreshToken()
    }
}