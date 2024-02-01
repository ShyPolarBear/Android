package com.shypolarbear.domain.repository.ranking

import com.shypolarbear.domain.model.ranking.MyRankingResponse
import com.shypolarbear.domain.model.ranking.RankingScroll
import com.shypolarbear.domain.model.ranking.TotalRankingResponse

interface RankingRepo {
    suspend fun loadTotalRankingResponse(rankingScroll: RankingScroll): Result<TotalRankingResponse>
    suspend fun loadMyRankingResponse(): Result<MyRankingResponse>
}
