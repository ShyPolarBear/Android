package com.shypolarbear.domain.repository.mypage

import com.shypolarbear.domain.model.mypage.MyCommentRequest
import com.shypolarbear.domain.model.mypage.MyCommentResponse
import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse

interface MyFeedRepo {
    suspend fun getMyPostResponse(myPostRequest: MyPostRequest): Result<MyPostResponse>
    suspend fun getMyCommentResponse(myCommentRequest: MyCommentRequest): Result<MyCommentResponse>
}
