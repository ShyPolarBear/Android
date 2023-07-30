package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.usecase.feed.FeedCommentUseCase
import com.shypolarbear.domain.usecase.feed.FeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val feedDetailUseCase: FeedDetailUseCase,
    private val feedCommentUseCase: FeedCommentUseCase
): BaseViewModel() {

    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _feedCommentMock = MutableLiveData<List<Comment>>()
    val feedCommentMock: LiveData<List<Comment>> = _feedCommentMock

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            val feedDetailTestData = feedDetailUseCase.loadFeedDetailData(feedId)

            feedDetailTestData
                .onSuccess {
                    _feed.value = it.data
                    Timber.d(it.toString())
                }
                .onFailure {

                }
        }
    }

    fun loadFeedCommentMock(feedId: Int) {
        viewModelScope.launch {
            val feedCommentMockData = feedCommentUseCase.loadFeedCommentData(feedId)

            feedCommentMockData
                .onSuccess {
                    _feedCommentMock.value = it.data.comments
                    Timber.d(it.toString())
                }
                .onFailure {

                }
        }
    }
}