package com.shypolarbear.data.repositoryimpl.image

import com.shypolarbear.data.api.image.ImageApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.image.ImageDeleteRequest
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.repository.ImageRepo

class ImageRepoImpl(private val api: ImageApi) : ImageRepo {
    override suspend fun imageModifyRequest(imageModifyRequest: ImageModifyRequest): Result<ImageModifyResponse> {
        return try {
            val response = api.imageModify(imageModifyRequest)
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

    override suspend fun imageDeleteRequest(imageDeleteRequest: ImageDeleteRequest): Result<ImageDeleteResponse> {
        return try {
            val response = api.imageDelete(imageDeleteRequest)
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