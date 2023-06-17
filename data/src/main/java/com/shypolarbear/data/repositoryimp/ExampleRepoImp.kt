package com.shypolarbear.data.repositoryimp

import com.shypolarbear.data.api.ExampleApi
import com.shypolarbear.domain.model.ExampleModel
import com.shypolarbear.domain.repository.ExampleRepo
import javax.inject.Inject

class ExampleRepoImp @Inject constructor (
    private val api: ExampleApi
): ExampleRepo {
    override suspend fun getSampleData(): Result<ExampleModel>  = runCatching{
        return Result.success(api.getExample())
    }
}