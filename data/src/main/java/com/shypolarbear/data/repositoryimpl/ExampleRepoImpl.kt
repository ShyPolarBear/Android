package com.shypolarbear.data.repositoryimpl

import android.accounts.NetworkErrorException
import com.shypolarbear.data.api.ExampleApi
import com.shypolarbear.domain.model.ExampleModel
import com.shypolarbear.domain.repository.ExampleRepo
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class ExampleRepoImpl @Inject constructor (
    private val api: ExampleApi
): ExampleRepo {
    override suspend fun getSampleData(): Result<ExampleModel>  = runCatching{
        return Result.success(api.getExample().body()!!)
    }
}