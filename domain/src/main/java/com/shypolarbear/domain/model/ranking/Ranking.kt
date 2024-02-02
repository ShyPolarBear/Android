package com.shypolarbear.domain.model.ranking

data class Ranking(
    val rank: Int,
    val profileImage: String,
    val nickName: String,
    val point: Int,
    val winningPercent: Int,
    val rankingId: Int,
)

data class RankingScroll(
    val lastCommentId: Int?,
    val limit: Int?,
)
