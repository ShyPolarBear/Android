package com.shypolarbear.data.repositoryimpl

import com.shypolarbear.data.api.ExampleApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.sample.ExampleModel
import com.shypolarbear.domain.repository.ExampleRepo
import javax.inject.Inject

class ExampleRepoImpl @Inject constructor (
    private val api: ExampleApi
): ExampleRepo {
    override suspend fun getSampleData(): Result<ExampleModel> {
        return try {
            val response = api.getExample()
            when {
                response.isSuccessful -> Result.success(api.getExample().body()!!)
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}