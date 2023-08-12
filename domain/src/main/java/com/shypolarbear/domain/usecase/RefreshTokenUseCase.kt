package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.repository.TokenRepo

class RefreshTokenUseCase (
    private val repo: TokenRepo
){
    fun loadRefreshToken() {
        return repo.getRefreshToken()
    }
}