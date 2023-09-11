package com.shypolarbear.data.api.image

import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ImageUploadApi {
    @Multipart
    @POST("/api/images")
    suspend fun imageUpload(
        @Body
        imageUploadRequest: ImageUploadRequest,
    ): Response<ImageUploadResponse>
}