package com.shypolarbear.data.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object FormDataConverterUtil {
    private const val MIME_TYPE_IMAGE = "image/*"
    private const val MIME_TYPE_TEXT = "text/plain"

    fun getRequestBody(value: String): RequestBody {
        return value.toRequestBody(MIME_TYPE_TEXT.toMediaType())
    }

    fun getMultiPartBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }

    fun getMultiPartBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody(MIME_TYPE_IMAGE.toMediaType()),
        )
    }
}
