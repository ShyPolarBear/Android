package com.shypolarbear.domain.model.image

import java.io.File

data class ImageUrls(
    val imageUrls: List<String>,
)

data class ImageFiles(
    val imageFiles: List<File>,
)
