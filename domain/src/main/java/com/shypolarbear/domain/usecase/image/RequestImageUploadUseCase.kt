package com.shypolarbear.domain.usecase.image

import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUploadResponse
import com.shypolarbear.domain.repository.image.ImageUploadRepo

class RequestImageUploadUseCase(
    private val repo: ImageUploadRepo,
) {
    suspend operator fun invoke(imageUploadRequest: ImageUploadRequest): Result<ImageUploadResponse> {
        return repo.imageUploadRequest(imageUploadRequest)
    }
}
