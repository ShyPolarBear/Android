package com.shypolarbear.data.api.image

import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ImageEditApi {

    @Multipart
    @PUT("/api/images")
    suspend fun imageModify(
        @Part("type") type: RequestBody,
        @Part
        newImageFiles: List<MultipartBody.Part>,
        @Part("oldImageUrls")
        oldImageUrls: List<RequestBody>,
    ): Response<ImageModifyResponse>

    @DELETE("/api/images")
    suspend fun imageDelete(
        imageUrls: List<String>,
    ): Response<ImageDeleteResponse>

}