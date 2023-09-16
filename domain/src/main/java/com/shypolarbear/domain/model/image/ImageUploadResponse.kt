package com.shypolarbear.domain.model.image

data class ImageUploadResponse(
    val code: Int,
    val data: ImageUrls,
    val message: String,
)
