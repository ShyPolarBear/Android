package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse

interface ImageEditRepo {
    suspend fun imageModifyRequest(imageModifyRequest: ImageModifyRequest): Result<ImageModifyResponse>
    suspend fun imageDeleteRequest(imageUrls: List<String>): Result<ImageDeleteResponse>
}