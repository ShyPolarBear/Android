package com.shypolarbear.domain.repository.image

import com.shypolarbear.domain.model.image.ImageFiles
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse

interface ImageUploadRepo {
    suspend fun imageUploadRequest(imageUploadRequest: ImageUploadRequest): Result<ImageUploadResponse>
}