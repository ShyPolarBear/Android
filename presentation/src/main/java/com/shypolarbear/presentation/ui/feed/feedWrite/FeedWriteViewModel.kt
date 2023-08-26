package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class FeedWriteViewModel @Inject constructor(

): BaseViewModel(){
    private val _liveImgList = MutableLiveData<MutableList<FeedWriteImg>>(mutableListOf())
    val liveImgList: LiveData<MutableList<FeedWriteImg>> = _liveImgList

    fun addImgList(imgUri: List<Uri>) {
        val imgList: MutableList<FeedWriteImg> = _liveImgList.value!!
        val feedWriteImgList = imgUri.map { FeedWriteImg(it.toString()) }

        imgList.addAll(0, feedWriteImgList)
        _liveImgList.value = imgList
    }

    fun removeImgList(position: Int) {
        Timber.d("4")
        val imgList: MutableList<FeedWriteImg> = _liveImgList.value!!

        imgList.removeAt(position)
        _liveImgList.value = imgList
    }
}