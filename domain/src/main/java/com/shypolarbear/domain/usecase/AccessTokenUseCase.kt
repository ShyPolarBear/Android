package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.repository.TokenRepo

class AccessTokenUseCase (
    private val repo: TokenRepo
){
    suspend fun setAccessToken(accessToken: String){
        repo.setAccessToken(accessToken)
    }

    suspend operator fun invoke(): String {
        return repo.getAccessToken()
    }
}