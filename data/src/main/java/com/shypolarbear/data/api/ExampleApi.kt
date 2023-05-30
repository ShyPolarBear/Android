package com.shypolarbear.data.api

import com.shypolarbear.domain.model.ExampleModel
import retrofit2.http.GET

interface ExampleApi {
    @GET("")
    fun getExample(): ExampleModel
}