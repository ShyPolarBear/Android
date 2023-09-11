package com.shypolarbear.domain.model.image

data class ImageModifyRequest(
    val type: String,
    val newImageFiles: ImageFiles,
    val oldImageFiles: ImageUrls
)
