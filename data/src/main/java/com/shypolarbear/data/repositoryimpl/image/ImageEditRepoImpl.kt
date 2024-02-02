package com.shypolarbear.data.repositoryimpl.image

import com.shypolarbear.data.api.image.ImageEditApi
import com.shypolarbear.data.util.FormDataConverterUtil
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.model.image.ImageUrls
import com.shypolarbear.domain.repository.image.ImageEditRepo
import okhttp3.MultipartBody
import javax.inject.Inject

const val NEW_IMAGES = "newImageFiles"
const val OLD_IMAGES = "oldImageUrls"
class ImageEditRepoImpl @Inject constructor(private val api: ImageEditApi) : ImageEditRepo {
    override suspend fun imageModifyRequest(imageModifyRequest: ImageModifyRequest): Result<ImageModifyResponse> {
        return try {
            val typePart = FormDataConverterUtil.getRequestBody(imageModifyRequest.type)
            val newImageFiles: List<MultipartBody.Part> = imageModifyRequest.newImageFiles.map { file ->
                FormDataConverterUtil.getMultiPartBody(NEW_IMAGES, file)
            }
            val oldImageUrls: List<MultipartBody.Part> = imageModifyRequest.oldImageFiles.map { urls ->
                FormDataConverterUtil.getMultiPartBody(OLD_IMAGES, urls)
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
            val response = api.imageDelete(ImageUrls(imageUrls))
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
