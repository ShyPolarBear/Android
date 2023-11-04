package com.shypolarbear.domain.usecase.more

import com.shypolarbear.domain.model.more.CheckDuplicateNickNameResponse
import com.shypolarbear.domain.repository.InfoRepo

class RequestCheckDuplicateNickNameUseCase(
    private val repo: InfoRepo
) {
    suspend operator fun invoke(nickName: String): Result<CheckDuplicateNickNameResponse> {
        return repo.checkDuplicateNickName(nickName)
    }
}