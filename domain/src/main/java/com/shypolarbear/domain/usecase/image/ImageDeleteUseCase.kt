package com.shypolarbear.domain.usecase.image

import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.repository.ImageEditRepo

class ImageDeleteUseCase (
    private val repo: ImageEditRepo
) {
    suspend operator fun invoke(imageUrls: List<String>): Result<ImageDeleteResponse>{
        return repo.imageDeleteRequest(imageUrls)
    }
}