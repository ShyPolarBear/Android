package com.shypolarbear.domain.usecase

import com.shypolarbear.domain.model.ExampleModel
import com.shypolarbear.domain.repository.ExampleRepo

class ExampleUseCase(
    private val repo: ExampleRepo
) {
    suspend fun loadSampleData(): Result<ExampleModel> {
        return repo.getSampleData()
    }
}