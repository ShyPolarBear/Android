package com.shypolarbear.domain.usecase.more

import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.repository.InfoRepo

class RequestMyInfoChangeUseCase  (
    private val repo: InfoRepo
) {
    suspend operator fun invoke(
        nickName: String,
        profileImage: String?,
        email: String,
        phoneNumber: String
    ): Result<InfoResponse> {
        return repo.changeMyInfo(nickName, profileImage, email, phoneNumber)
    }
}