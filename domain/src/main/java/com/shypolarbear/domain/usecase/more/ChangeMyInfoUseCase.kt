package com.shypolarbear.domain.usecase.more

import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.repository.InfoRepo

class ChangeMyInfoUseCase  (
    private val repo: InfoRepo
) {
    suspend fun requestChangeMyInfo(
        nickName: String,
        profileImage: String?,
        email: String,
        phoneNumber: String
    ): Result<InfoResponse> {
        return repo.changeMyInfo(nickName, profileImage, email, phoneNumber)
    }
}