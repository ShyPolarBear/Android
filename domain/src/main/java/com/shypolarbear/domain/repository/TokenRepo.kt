package com.shypolarbear.domain.repository

interface TokenRepo {
    fun getAccessToken()

    fun getRefreshToken()
}