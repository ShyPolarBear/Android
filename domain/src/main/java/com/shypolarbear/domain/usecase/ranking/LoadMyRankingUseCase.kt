package com.shypolarbear.domain.usecase.ranking

import com.shypolarbear.domain.model.ranking.MyRankingResponse
import com.shypolarbear.domain.repository.ranking.RankingRepo

class LoadMyRankingUseCase(
    private val repo: RankingRepo
) {
    suspend operator fun invoke(): Result<MyRankingResponse>{
        return repo.loadMyRankingResponse()
    }
}