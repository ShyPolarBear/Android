package com.shypolarbear.data.api

import com.shypolarbear.domain.model.image.ImageDeleteRequest
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT

interface ImageApi {

    @Multipart
    @POST("/api/images")
    suspend fun imageUpload(
        @Body
        imageUploadRequest: ImageUploadRequest,
    ): Response<ImageUploadResponse>

    @Multipart
    @PUT("/api/images")
    suspend fun imageModify(
        @Body
        imageModifyRequest: ImageModifyRequest,
    ): Response<ImageModifyResponse>

    @DELETE("/api/images")
    suspend fun imageDelete(
        @Body
        imageDeleteRequest: ImageDeleteRequest,
    ): Response<ImageDeleteResponse>

}