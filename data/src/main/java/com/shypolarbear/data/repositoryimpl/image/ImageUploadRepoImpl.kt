package com.shypolarbear.data.repositoryimpl.image

import com.shypolarbear.data.api.image.ImageUploadApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import com.shypolarbear.domain.repository.image.ImageUploadRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ImageUploadRepoImpl @Inject constructor(private val api: ImageUploadApi) : ImageUploadRepo {
    override suspend fun imageUploadRequest(imageUploadRequest: ImageUploadRequest): Result<ImageUploadResponse> {
        return try {
            val fileList = imageUploadRequest.imageFiles
            val imageFiles: List<MultipartBody.Part> = fileList.map { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("imageFiles", file.name, requestFile)
            }
            val typePart = imageUploadRequest.type.toRequestBody("text/plain".toMediaTypeOrNull())

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