package com.shypolarbear.domain.usecase.mypage

import com.shypolarbear.domain.model.mypage.MyCommentRequest
import com.shypolarbear.domain.model.mypage.MyCommentResponse
import com.shypolarbear.domain.repository.mypage.MyFeedRepo

class LoadMyCommentUseCase(
    private val repo: MyFeedRepo
) {
    suspend operator fun invoke(getMyCommentRequest: MyCommentRequest): Result<MyCommentResponse>{
        return repo.getMyCommentResponse(getMyCommentRequest)
    }
}