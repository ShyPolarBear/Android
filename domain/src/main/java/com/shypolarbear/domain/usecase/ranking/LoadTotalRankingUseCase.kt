package com.shypolarbear.domain.usecase.ranking

import com.shypolarbear.domain.model.ranking.RankingScroll
import com.shypolarbear.domain.model.ranking.TotalRankingResponse
import com.shypolarbear.domain.repository.ranking.RankingRepo

class LoadTotalRankingUseCase(
    private val repo: RankingRepo,
) {
    suspend operator fun invoke(rankingScroll: RankingScroll): Result<TotalRankingResponse> {
        return repo.loadTotalRankingResponse(rankingScroll)
    }
}
