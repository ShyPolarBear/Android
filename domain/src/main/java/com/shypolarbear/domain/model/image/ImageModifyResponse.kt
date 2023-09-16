package com.shypolarbear.domain.model.image

data class ImageModifyResponse(
    val code: Int,
    val data: ImageUrls,
    val message: String
)
