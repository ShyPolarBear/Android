package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.InfoApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.more.InfoResponse
import com.shypolarbear.domain.model.more.ChangeInfoRequest
import com.shypolarbear.domain.repository.InfoRepo
import javax.inject.Inject

class InfoRepoImpl @Inject constructor(
    private val api: InfoApi
): InfoRepo {
    override suspend fun getMyInfo(): Result<InfoResponse> {
        return try {
            val response = api.getMyInfo()
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

    override suspend fun changeMyInfo(
        nickName: String,
        profileImage: String?,
        email: String,
        phoneNumber: String
    ): Result<InfoResponse> {
        return try {
            val response = api.changeMyInfo(ChangeInfoRequest(
                nickName = nickName,
                profileImage = "NULL",  // TODO("이미지 작업 완료되면 수정할 예정")
                email = email,
                phoneNumber = phoneNumber)
            )
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