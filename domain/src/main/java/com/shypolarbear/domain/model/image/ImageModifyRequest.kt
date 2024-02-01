package com.shypolarbear.domain.model.image

import java.io.File

data class ImageModifyRequest(
    val type: String,
    val newImageFiles: List<File>,
    val oldImageFiles: List<String>,
)
