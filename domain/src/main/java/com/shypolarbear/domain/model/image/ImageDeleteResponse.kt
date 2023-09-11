package com.shypolarbear.domain.model.image

data class ImageDeleteResponse(
    val code: Int,
    val data: Delete,
    val message: String
)

data class Delete(
    val deletedImageCount: Int
)
