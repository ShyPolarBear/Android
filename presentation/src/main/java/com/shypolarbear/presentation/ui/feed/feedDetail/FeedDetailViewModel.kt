package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.base.BaseViewModel

class FeedDetailViewModel: BaseViewModel() {

    private val _feedComment = MutableLiveData<List<FeedComment>>()
    val feedComment: LiveData<List<FeedComment>> = _feedComment

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