package com.shypolarbear.data.repositoryimpl.image

import com.shypolarbear.data.api.image.ImageEditApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.repository.ImageEditRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ImageEditRepoImpl @Inject constructor(private val api: ImageEditApi) : ImageEditRepo {
    override suspend fun imageModifyRequest(imageModifyRequest: ImageModifyRequest): Result<ImageModifyResponse> {
        return try {
            val typePart = imageModifyRequest.type.toRequestBody("text/plain".toMediaTypeOrNull())
            val newImageFiles: List<MultipartBody.Part> = imageModifyRequest.newImageFiles.map { file ->
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("newImageFiles", file.name, requestFile)
            }
            val oldImageUrls: List<MultipartBody.Part> = imageModifyRequest.oldImageFiles.map { urls->
                MultipartBody.Part.createFormData("oldImageUrls", urls)
            }

            val response = api.imageModify(typePart, newImageFiles, oldImageUrls)
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

    override suspend fun imageDeleteRequest(imageUrls: List<String>): Result<ImageDeleteResponse> {
        return try {
            val response = api.imageDelete(imageUrls)
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