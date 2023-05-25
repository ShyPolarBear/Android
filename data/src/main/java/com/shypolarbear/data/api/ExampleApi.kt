package com.shypolarbear.data.api

import retrofit2.http.GET

interface ExampleApi {

    @GET("")
    fun getExample(): ExampleApi
}