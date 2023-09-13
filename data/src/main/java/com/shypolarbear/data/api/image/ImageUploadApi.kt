package com.shypolarbear.data.api.image

import com.shypolarbear.domain.model.image.ImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageUploadApi {
    @Multipart
    @POST("/api/images")
    suspend fun imageUpload(
        @Part("type") type: RequestBody,
        @Part
        imageFiles: List<MultipartBody.Part>,
    ): Response<ImageUploadResponse>
}