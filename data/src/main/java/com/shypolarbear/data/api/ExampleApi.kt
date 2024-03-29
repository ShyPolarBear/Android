package com.shypolarbear.data.api

import com.shypolarbear.domain.model.sample.ExampleModel
import retrofit2.Response
import retrofit2.http.GET

interface ExampleApi {
    @GET("jokes/categories")
    suspend fun getExample(): Response<ExampleModel>
}
