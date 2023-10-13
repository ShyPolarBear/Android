package com.shypolarbear.data.repositoryimpl.ranking

import com.shypolarbear.data.api.ranking.RankingApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.ranking.MyRankingResponse
import com.shypolarbear.domain.model.ranking.RankingScroll
import com.shypolarbear.domain.model.ranking.TotalRankingResponse
import com.shypolarbear.domain.repository.ranking.RankingRepo
import javax.inject.Inject

class RankingRepoImpl @Inject constructor(private val api: RankingApi) : RankingRepo {
    override suspend fun loadTotalRankingResponse(rankingScroll: RankingScroll): Result<TotalRankingResponse> {
        return try {
            val response = api.loadTotalRanking(rankingScroll.lastCommentId, rankingScroll.limit)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }

                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadMyRankingResponse(): Result<MyRankingResponse> {
        return try {
            val response = api.loadMyRanking()
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
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