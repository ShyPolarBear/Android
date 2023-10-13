package com.shypolarbear.data.api.ranking

import com.shypolarbear.domain.model.ranking.MyRankingResponse
import com.shypolarbear.domain.model.ranking.TotalRankingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RankingApi {

    @GET("/api/rankings/me")
    suspend fun loadMyRanking(): Response<MyRankingResponse>

    @GET("/api/rankings")
    suspend fun loadTotalRanking(
        @Query("lastRankingId") lastRankingId: Int?,
        @Query("limit") limit: Int?,
    ): Response<TotalRankingResponse>


}