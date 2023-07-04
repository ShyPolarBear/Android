package com.shypolarbear.presentation.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.presentation.base.BaseViewModel

class FeedViewModel: BaseViewModel() {
    private val _feedPost = MutableLiveData<List<FeedPost>>()
    val feedPost: LiveData<List<FeedPost>> = _feedPost

    fun loadFeedPost() {
        _feedPost.value = mutableListOf(
            // 테스트 데이터
            FeedPost("1"),
            FeedPost("1"),
            FeedPost("1"),
            FeedPost("1")
        )
    }
}