package com.shypolarbear.domain.usecase.more

import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.repository.InfoRepo

class LoadMyInfoUseCase(
    private val repo: InfoRepo,
) {
    suspend operator fun invoke(): Result<InfoResponse> {
        return repo.getMyInfo()
    }
}
