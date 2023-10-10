package com.shypolarbear.domain.model.ranking

data class TotalRankingResponse(
    val code: Int,
    val data: TotalRanking,
    val message: String
)

data class TotalRanking(
    val count: Int,
    val last: Boolean,
    val content: List<Ranking>
)