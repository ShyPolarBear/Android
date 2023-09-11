package com.shypolarbear.domain.usecase.image

import com.shypolarbear.domain.model.image.ImageDeleteRequest
import com.shypolarbear.domain.model.image.ImageDeleteResponse
import com.shypolarbear.domain.repository.ImageRepo

class ImageDeleteUseCase (
    private val repo: ImageRepo
) {
    suspend operator fun invoke(imageDeleteRequest: ImageDeleteRequest): Result<ImageDeleteResponse>{
        return repo.imageDeleteRequest(imageDeleteRequest)
    }
}