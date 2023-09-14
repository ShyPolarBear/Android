package com.shypolarbear.data.api.image

import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.model.image.ImageUrls
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.Headers
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
        @Part
        oldImageUrls: List<MultipartBody.Part>,
    ): Response<ImageModifyResponse>

    @HTTP(method = "DELETE", path = "/api/images", hasBody = true)
    suspend fun imageDelete(
        @Body
        imageUrls: ImageUrls,
    ): Response<ImageDeleteResponse>

}