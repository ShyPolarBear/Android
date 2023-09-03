package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
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
    private val feedDetailUseCase: FeedDetailUseCase        // 테스트 용 TODO("전체 피드 조회 api 구현 시 제거")
): BaseViewModel() {

    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    fun loadFeedTotalData() {
        viewModelScope.launch {
            val feedData = feedTotalUseCase.loadFeedTotalData()
            val feedDetailTestData = feedDetailUseCase.loadFeedDetailData(1)

            feedData
                .onSuccess {
                    when {
                        // 처음 로딩하는 경우
                        _feed.value.isNullOrEmpty() -> {
                            val feedList = mutableListOf<Feed>()
                            feedList.addAll(it.data.feeds)
                            feedList.add(Feed())            // progress bar

                            _feed.value = feedList
                        }
                        // 다음 페이지 로딩하는 경우
                        else -> {
                            // 테스트 용 TODO("전체 피드 조회 api 구현 시 수정")
                            feedDetailTestData
                                .onSuccess { feedDetail ->
                                    val feedList = _feed.value as MutableList<Feed>
                                    feedList.removeLast()
                                    feedList.addAll(listOf(feedDetail.data))
                                    feedList.add(Feed())            // progress bar

                                    _feed.value = feedList
                                    Timber.d("피드 리스트 개수: ${feedList.size}")
                                }
                                .onFailure {

                                }
                        }

                    }

//                    val newDataList = it.data.feeds
//                    val currentList = _feed.value ?: emptyList()
//                    _feed.value = currentList + newDataList
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
}