package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.domain.usecase.feed.FeedDeleteUseCase
import com.shypolarbear.domain.usecase.feed.FeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.FeedLikeUseCase
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedTotalViewModel @Inject constructor (
    private val feedTotalUseCase: FeedTotalUseCase,
    private val feedDeleteUseCase: FeedDeleteUseCase,
    private val feedLikeUseCase: FeedLikeUseCase,
): BaseViewModel() {

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    var feedIsLast = false

    fun loadFeedTotalData(sort: String) {
        var feedData: Result<FeedTotal>

        viewModelScope.launch {
            feedData = when {
                _feed.value.isNullOrEmpty() -> { feedTotalUseCase.loadFeedTotalData(sort, lastFeedId = null) }
                else -> { feedTotalUseCase.loadFeedTotalData(sort, _feed.value!![_feed.value!!.lastIndex - 1].feedId) }
            }

            feedData
                .onSuccess {
                    val newDataList = it.data.content
                    val currentList = _feed.value ?: emptyList()

                    if (!currentList.isNullOrEmpty()) {
                        val removeProgressList = currentList as MutableList
                        removeProgressList.removeLast()

                        _feed.value = removeProgressList
                    }

                    feedIsLast = it.data.last

                    when(feedIsLast) {
                        true -> { _feed.value = currentList + newDataList }
                        false -> { _feed.value = currentList + newDataList + listOf(Feed()) }
                    }
                }
                .onFailure {

                }
        }
    }

    fun requestDeleteFeed(feedId: Int) {
        viewModelScope.launch {
            feedDeleteUseCase.requestDeleteFeed(feedId)
        }
    }

    fun clickFeedLikeBtn(isLiked: Boolean, likeCnt: Int, feedId: Int) {
        val currentFeed = _feed.value?: return
        val updatedFeed = currentFeed.map { feed ->

            if (feed.feedId == feedId)
                feed.copy(isLike = isLiked, likeCount = likeCnt)
            else
                feed
        }
        viewModelScope.launch {
            feedLikeUseCase.requestLikeFeed(feedId)
        }
        _feed.value = updatedFeed
    }

    fun clickFeedBestCommentLikeBtn(isLiked: Boolean, likeCnt: Int, feedId: Int) {
        val currentFeed = _feed.value?: return
        val updatedFeed = currentFeed.map { feed ->

            if (feed.feedId == feedId) {
                val updatedBestComment = feed.comment.copy(isLike = isLiked, likeCount = likeCnt)
                feed.copy(comment = updatedBestComment)
            }
            else
                feed
        }
        _feed.value = updatedFeed
    }

    fun removeFeedList(position: Int) {
        val feedList: MutableList<Feed> = mutableListOf()

        feedList.addAll(0, _feed.value!!)

        feedList.removeAt(position)
        _feed.value = feedList
    }

    fun clearFeedList() {
        val feedList: MutableList<Feed> = mutableListOf()

        _feed.value = feedList
    }
}