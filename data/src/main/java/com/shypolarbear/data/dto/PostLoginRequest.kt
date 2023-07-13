package com.shypolarbear.data.dto

import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("socialType") val socialType: String
)
