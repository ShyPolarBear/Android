package com.shypolarbear.domain.usecase.image

import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.repository.image.ImageEditRepo

class RequestImageDeleteUseCase(
    private val repo: ImageEditRepo,
) {
    suspend operator fun invoke(imageUrls: List<String>): Result<ImageDeleteResponse> {
        return repo.imageDeleteRequest(imageUrls)
    }
}
