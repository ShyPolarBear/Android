package com.shypolarbear.domain.model.image

import java.io.File

data class ImageUrls(
    val imageLinks: List<String>,
)

data class ImageFiles(
    val imageFiles: List<File>,
)
