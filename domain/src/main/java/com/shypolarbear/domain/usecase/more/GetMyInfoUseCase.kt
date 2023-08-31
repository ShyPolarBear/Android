package com.shypolarbear.domain.usecase.more

import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.repository.InfoRepo

class GetMyInfoUseCase (
    private val repo: InfoRepo
){
    suspend fun loadMyInfo(): Result<InfoResponse> {
        return repo.getMyInfo()
    }
}