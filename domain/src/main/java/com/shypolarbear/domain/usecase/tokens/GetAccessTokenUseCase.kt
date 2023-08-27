package com.shypolarbear.domain.usecase.tokens

import com.shypolarbear.domain.repository.TokenRepo

class GetAccessTokenUseCase (
    private val repo: TokenRepo
){
    suspend operator fun invoke(): String {
        return repo.getAccessToken()
    }
}