package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.more.LogoutResponse

interface LogoutRepo {
    suspend fun requestLogoutData(): Result<LogoutResponse>
}