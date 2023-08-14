package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.domain.repository.TokenRepo
import javax.inject.Inject

class TokenRepoImpl @Inject constructor(

): TokenRepo {
    override fun getAccessToken() {
        TODO("DataStore에서 Access Token 가져오는 동작")
    }

    override fun getRefreshToken() {
        TODO("DataStore에서 Refresh Token 가져오는 동작")
    }
}