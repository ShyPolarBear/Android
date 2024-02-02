package com.shypolarbear.presentation.ui.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.ranking.Ranking
import com.shypolarbear.domain.model.ranking.RankingScroll
import com.shypolarbear.domain.model.ranking.TotalRanking
import com.shypolarbear.domain.usecase.ranking.LoadMyRankingUseCase
import com.shypolarbear.domain.usecase.ranking.LoadTotalRankingUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.simpleHttpErrorCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val loadMyRankingUseCase: LoadMyRankingUseCase,
    private val loadTotalRankingUseCase: LoadTotalRankingUseCase,
) : BaseViewModel() {

    private val _myRankingResponse = MutableLiveData<Ranking>()
    val myRankingResponse: LiveData<Ranking> = _myRankingResponse
    private val _totalRankingResponse = MutableLiveData<TotalRanking>()
    val totalRankingResponse: LiveData<TotalRanking> = _totalRankingResponse

    fun loadRankingData() {
        loadMyRanking()
        loadTotalRanking()
    }

    private fun loadMyRanking() {
        viewModelScope.launch {
            val responseMyRanking =
                loadMyRankingUseCase()
            responseMyRanking.onSuccess { response ->
                _myRankingResponse.value = response.data
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }

    private fun loadTotalRanking(lastCommentId: Int? = null): Job {
        val loadJob = viewModelScope.launch {
            val responseMyRanking =
                loadTotalRankingUseCase(RankingScroll(lastCommentId, limit = null))
            Timber.tag("RANKING").d("$responseMyRanking")

            responseMyRanking.onSuccess { response ->
                _totalRankingResponse.value = response.data
                Timber.tag("RANKING").d("${_totalRankingResponse.value}")
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
        return loadJob
    }

    fun loadMoreRanking() {
        viewModelScope.launch {
            if (!_totalRankingResponse.value!!.last) {
                val loadJob = loadTotalRanking(
                    _totalRankingResponse.value!!.content.last().rankingId,
                )
                loadJob.join()
            } else {
                // isLast = true
            }
        }
    }
}
