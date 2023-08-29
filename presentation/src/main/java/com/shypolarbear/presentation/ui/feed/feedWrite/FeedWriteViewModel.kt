package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.domain.usecase.feed.ChangePostUseCase
import com.shypolarbear.domain.usecase.feed.FeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.FeedWriteUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val feedDetailUseCase: FeedDetailUseCase,
    private val changePostUseCase: ChangePostUseCase,
    private val feedWriteUseCase: FeedWriteUseCase
): BaseViewModel(){
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _liveImgList = MutableLiveData<MutableList<FeedWriteImg>>(mutableListOf())
    val liveImgList: LiveData<MutableList<FeedWriteImg>> = _liveImgList

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            val feedDetailData = feedDetailUseCase.loadFeedDetailData(feedId)

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
            changePostUseCase.requestChangePost(feedId, content, feedImages, title)
        }
    }

    fun writePost(title: String, content: String, feedImages: List<String>?) {
        viewModelScope.launch {
            feedWriteUseCase.requestWriteFeed(title, content, feedImages)
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