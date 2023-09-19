package com.shypolarbear.data.api.mypage

import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP

interface MyPostApi {

    @HTTP(method = "GET", path = "/api/user/feeds", hasBody = true)
    suspend fun getMyPost(
        @Body
        request: MyPostRequest = MyPostRequest(null, null)
    ): Response<MyPostResponse>
}