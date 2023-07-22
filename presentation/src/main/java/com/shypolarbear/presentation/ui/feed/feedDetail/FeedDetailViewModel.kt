package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.usecase.feed.FeedUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val feedUseCase: FeedUseCase
): BaseViewModel() {

    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _feedComment = MutableLiveData<List<FeedComment>>()
    val feedComment: LiveData<List<FeedComment>> = _feedComment

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            val feedDetailTestData = feedUseCase.loadFeedDetailData(feedId)

            feedDetailTestData
                .onSuccess {
                    _feed.value = it.data
                    Timber.d(it.toString())
                }
                .onFailure {

                }
        }
    }

    fun loadFeedComment() {
        _feedComment.value = mutableListOf(
            // 테스트 데이터
            FeedComment("1", 0, "my"),
            FeedComment("1", 0, "other"),
            FeedComment("1", 2, "my"),
            FeedComment("1", 3, "other"),
            FeedComment("1", 1, "my"),
            FeedComment("1", 0, "other"),
            FeedComment("1", 2, "other"),
            FeedComment("1", 2, "my"),
        )
    }
}