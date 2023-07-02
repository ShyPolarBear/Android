package com.shypolarbear.presentation.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.base.BaseViewModel

class FeedViewModel: BaseViewModel() {
    private val _feedPostImgUrl = MutableLiveData<List<FeedPostImg>>()
    val feedPostImgUrl: LiveData<List<FeedPostImg>> = _feedPostImgUrl

    private val _feedPost = MutableLiveData<List<FeedPost>>()
    val feedPost: LiveData<List<FeedPost>> = _feedPost

    // test 코드
    fun loadFeedPostImg(){
        // 테스트 데이터
        _feedPostImgUrl.value = mutableListOf(
            FeedPostImg("https://github.com/BeamjunCho9/Smart_attendance_check_app/assets/107917980/45b2f385-f195-40d9-bf72-71b4f22a5e11"),
            FeedPostImg("https://github.com/BeamjunCho9/Smart_attendance_check_app/assets/107917980/45b2f385-f195-40d9-bf72-71b4f22a5e11"),
            FeedPostImg("https://github.com/BeamjunCho9/Smart_attendance_check_app/assets/107917980/45b2f385-f195-40d9-bf72-71b4f22a5e11")
        )
    }

    fun loadFeedPost() {
        // 테스트 데이터
        _feedPost.value = mutableListOf(
            FeedPost("1"),
            FeedPost("1"),
            FeedPost("1"),
            FeedPost("1")
        )
    }
}