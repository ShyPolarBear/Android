package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.usecase.feed.FeedUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedTotalViewModel @Inject constructor (
    private val feedTotalUseCase: FeedUseCase
): BaseViewModel() {

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    fun loadFeedTotalData() {
        viewModelScope.launch {
            val testData = feedTotalUseCase.loadFeedTotalData()

            testData
                .onSuccess {
                    _feed.value = it.data.feeds
                }
                .onFailure {

                }

        }
    }
}