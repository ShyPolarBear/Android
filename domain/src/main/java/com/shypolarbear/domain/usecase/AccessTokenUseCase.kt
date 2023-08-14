package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.repository.TokenRepo

class AccessTokenUseCase (
    private val repo: TokenRepo
){
    fun loadAccessToken(): String {
        return repo.getAccessToken()
    }
}