package com.shypolarbear.data.repositoryimpl.mypage

import com.shypolarbear.data.api.mypage.MyPostApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse
import com.shypolarbear.domain.repository.mypage.MyPostRepo
import javax.inject.Inject

class MyPostRepoImpl @Inject constructor(private val api: MyPostApi): MyPostRepo {
    override suspend fun getMyPostResponse(myPostRequest: MyPostRequest): Result<MyPostResponse> {
        return try {
            val response = api.getMyPost(myPostRequest)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}