package com.shypolarbear.data.repositoryimpl.mypage

import com.shypolarbear.data.api.mypage.MyFeedApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.mypage.MyCommentRequest
import com.shypolarbear.domain.model.mypage.MyCommentResponse
import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.model.mypage.MyPostResponse
import com.shypolarbear.domain.repository.mypage.MyFeedRepo
import javax.inject.Inject

class MyFeedRepoImpl @Inject constructor(private val api: MyFeedApi): MyFeedRepo {
    override suspend fun getMyPostResponse(myPostRequest: MyPostRequest): Result<MyPostResponse> {
        return try {
            val response = api.getMyPost(myPostRequest.lastFeedId, myPostRequest.limit)
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

    override suspend fun getMyCommentResponse(myCommentRequest: MyCommentRequest): Result<MyCommentResponse> {
        return try {
            val response = api.getMyComment(myCommentRequest.lastCommentId, myCommentRequest.limit)
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