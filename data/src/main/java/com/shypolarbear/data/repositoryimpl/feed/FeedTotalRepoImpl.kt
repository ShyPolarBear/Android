package com.shypolarbear.data.repositoryimpl.feed

import com.shypolarbear.data.api.feed.FeedApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.feed.Data
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.repository.feed.FeedTotalRepo
import timber.log.Timber
import javax.inject.Inject

class FeedTotalRepoImpl @Inject constructor(
    private val api: FeedApi
): FeedTotalRepo {
    override suspend fun getFeedTotalData(): Result<FeedTotal> {
        return try {
            val response = api.getFeedTotal()
            when {
                response.isSuccessful -> {
                    Result.success(api.getFeedTotal().body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}