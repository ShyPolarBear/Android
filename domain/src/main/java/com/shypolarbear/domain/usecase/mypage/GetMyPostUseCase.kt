package com.shypolarbear.domain.usecase.mypage

import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse
import com.shypolarbear.domain.repository.mypage.MyPostRepo

class GetMyPostUseCase(
    private val repo: MyPostRepo
) {
    suspend operator fun invoke(getMyPostRequest: MyPostRequest): Result<MyPostResponse>{
        return repo.getMyPostResponse(getMyPostRequest)
    }
}