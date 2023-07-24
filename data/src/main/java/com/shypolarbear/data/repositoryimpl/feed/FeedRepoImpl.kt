package com.shypolarbear.data.repositoryimpl.feed

import com.shypolarbear.data.api.feed.FeedApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.model.feed.feedDetail.FeedCommentMock
import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import com.shypolarbear.domain.repository.feed.FeedRepo
import javax.inject.Inject

class FeedRepoImpl @Inject constructor(
    private val api: FeedApi
): FeedRepo {
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

    override suspend fun getFeedDetailData(feedId: Int): Result<FeedDetail> {
        return try {
            val response = api.getFeedDetail(feedId)
            when {
                response.isSuccessful -> {
                    Result.success(api.getFeedDetail(feedId).body()!!)
                }
                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFeedCommentData(feedId: Int): Result<FeedCommentMock> {
        return try {
            val response = api.getFeedComment(feedId)
            when {
                response.isSuccessful -> {
                    Result.success(api.getFeedComment(feedId).body()!!)
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