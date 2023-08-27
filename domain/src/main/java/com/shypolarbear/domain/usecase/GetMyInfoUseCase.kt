package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.more.GetInfoResponse
import com.shypolarbear.domain.repository.InfoRepo

class GetMyInfoUseCase (
    private val repo: InfoRepo
){
    suspend fun loadMyInfo(): Result<GetInfoResponse> {
        return repo.getMyInfo()
    }
}