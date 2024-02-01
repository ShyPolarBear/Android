package com.shypolarbear.domain.model.image

import java.io.File

data class ImageUploadRequest(
    val type: String,
    val imageFiles: List<File>,
)
