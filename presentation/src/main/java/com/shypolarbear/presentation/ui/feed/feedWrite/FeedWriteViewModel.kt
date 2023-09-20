package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.domain.usecase.feed.RequestFeedChangeUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedWriteUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val feedDetailUseCase: LoadFeedDetailUseCase,
    private val changePostUseCase: RequestFeedChangeUseCase,
    private val feedWriteUseCase: RequestFeedWriteUseCase
): BaseViewModel(){
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _liveImgList = MutableLiveData<MutableList<FeedWriteImg>>(mutableListOf())
    val liveImgList: LiveData<MutableList<FeedWriteImg>> = _liveImgList

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            val feedDetailData = feedDetailUseCase(feedId)

            feedDetailData
                .onSuccess {
                    _feed.value = it.data
                }
                .onFailure {

                }
        }
    }

    fun changePost(feedId: Int, content: String, feedImages: List<String>?, title: String) {
        viewModelScope.launch {
            changePostUseCase(feedId, content, feedImages, title)
        }
    }

    fun writePost(title: String, content: String, feedImages: List<String>?) {
        viewModelScope.launch {
            feedWriteUseCase(title, content, feedImages)
        }
    }

    fun addImgList(imgUri: List<Uri>) {
        val imgList: MutableList<FeedWriteImg> = _liveImgList.value!!
        val feedWriteImgList = imgUri.map { FeedWriteImg(it.toString()) }

        imgList.addAll(0, feedWriteImgList)
        _liveImgList.value = imgList
    }

    fun removeImgList(position: Int) {
        val imgList: MutableList<FeedWriteImg> = _liveImgList.value!!

        imgList.removeAt(position)
        _liveImgList.value = imgList
    }
}