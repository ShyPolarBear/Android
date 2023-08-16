package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.repository.TokenRepo

class AccessTokenUseCase (
    private val repo: TokenRepo
){
    operator fun invoke(): String {
        return repo.getAccessToken()
    }
}