package com.shypolarbear.domain.usecase.image

import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageModifyResponse
import com.shypolarbear.domain.repository.ImageEditRepo

class ImageModifyUseCase(
    private val repo: ImageEditRepo
) {
    suspend operator fun invoke(imageModifyRequest: ImageModifyRequest): Result<ImageModifyResponse>{
        return repo.imageModifyRequest(imageModifyRequest)
    }
}