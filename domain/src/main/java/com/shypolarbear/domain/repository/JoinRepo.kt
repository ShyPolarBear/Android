package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.model.join.JoinResponse

interface JoinRepo {
    suspend fun getJoinResponse(joinRequest: JoinRequest): Result<JoinResponse>
}