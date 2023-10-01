package com.shypolarbear.domain.repository.mypage

import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse

interface MyPostRepo {
    suspend fun getMyPostResponse(myPostRequest: MyPostRequest): Result<MyPostResponse>
}