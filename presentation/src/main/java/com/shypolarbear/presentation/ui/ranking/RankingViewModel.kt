package com.shypolarbear.presentation.ui.ranking

import com.shypolarbear.domain.usecase.ranking.LoadMyRankingUseCase
import com.shypolarbear.domain.usecase.ranking.LoadTotalRankingUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val loadMyRankingUseCase: LoadMyRankingUseCase,
    private val loadTotalRankingUseCase: LoadTotalRankingUseCase
) : BaseViewModel() {

}