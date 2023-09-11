package com.shypolarbear.domain.usecase.image

import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import com.shypolarbear.domain.repository.ImageRepo

class ImageUploadUseCase(
    private val repo: ImageRepo
) {
    suspend operator fun invoke(imageUploadRequest: ImageUploadRequest): Result<ImageUploadResponse>{
        return repo.imageUploadRequest(imageUploadRequest)
    }
}