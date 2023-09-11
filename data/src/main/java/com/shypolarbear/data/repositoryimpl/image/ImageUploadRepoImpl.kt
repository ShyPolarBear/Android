package com.shypolarbear.data.repositoryimpl.image

import com.shypolarbear.data.api.image.ImageUploadApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import com.shypolarbear.domain.repository.image.ImageUploadRepo

class ImageUploadRepoImpl(private val api: ImageUploadApi):ImageUploadRepo {
    override suspend fun imageUploadRequest(imageUploadRequest: ImageUploadRequest): Result<ImageUploadResponse> {
        return try {
            val response = api.imageUpload(imageUploadRequest)
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