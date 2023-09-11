package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.image.ImageDeleteRequest
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse

interface ImageRepo {
    suspend fun imageModifyRequest(imageModifyRequest: ImageModifyRequest): Result<ImageModifyResponse>
    suspend fun imageDeleteRequest(imageDeleteRequest: ImageDeleteRequest): Result<ImageDeleteResponse>
}