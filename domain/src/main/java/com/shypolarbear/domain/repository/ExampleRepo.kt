package com.shypolarbear.domain.repository

import com.shypolarbear.domain.model.sample.ExampleModel

interface ExampleRepo {
    suspend fun getSampleData(): Result<ExampleModel>
}
