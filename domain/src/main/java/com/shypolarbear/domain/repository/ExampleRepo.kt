package com.shypolarbear.domain.repository
import com.shypolarbear.domain.model.ExampleModel

interface ExampleRepo {

    suspend fun getSampleData(): ExampleModel
}