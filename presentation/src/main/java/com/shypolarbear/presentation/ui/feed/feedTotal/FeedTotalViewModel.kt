package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedTotalViewModel @Inject constructor (
    private val feedTotalUseCase: FeedTotalUseCase
): BaseViewModel() {

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    fun loadFeedTotalData() {
        viewModelScope.launch {
            val feedData = feedTotalUseCase.loadFeedTotalData()

            feedData
                .onSuccess {
                    _feed.value = it.data.feeds
                }
                .onFailure {

                }

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
}