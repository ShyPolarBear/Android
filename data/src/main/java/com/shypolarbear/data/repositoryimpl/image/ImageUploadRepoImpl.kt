package com.shypolarbear.data.repositoryimpl.image

import com.shypolarbear.data.api.image.ImageUploadApi
import com.shypolarbear.data.util.FormDataConverterUtil
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import com.shypolarbear.domain.repository.image.ImageUploadRepo
import okhttp3.MultipartBody
import javax.inject.Inject

const val IMAGE_FILES = "imageFiles"

class ImageUploadRepoImpl @Inject constructor(private val api: ImageUploadApi) : ImageUploadRepo {
    override suspend fun imageUploadRequest(imageUploadRequest: ImageUploadRequest): Result<ImageUploadResponse> {
        return try {
            val typePart = FormDataConverterUtil.getRequestBody(imageUploadRequest.type)
            val imageFiles: List<MultipartBody.Part> = imageUploadRequest.imageFiles.map { file ->
                FormDataConverterUtil.getMultiPartBody(IMAGE_FILES, file)
            }

            val response = api.imageUpload(typePart, imageFiles)
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
