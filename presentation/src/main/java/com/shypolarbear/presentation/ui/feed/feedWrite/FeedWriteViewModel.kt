package com.shypolarbear.presentation.ui.feed.feedWrite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedWriteViewModel @Inject constructor(

): BaseViewModel(){
    private val _testImgList = MutableLiveData<MutableList<FeedWriteImg>>()
    val testImgList: LiveData<MutableList<FeedWriteImg>> = _testImgList
    init {
        _testImgList.value = mutableListOf()
    }

    fun addImgList() {
        _testImgList.value!!.add(0, FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/30b3d3c8-f2d8-4760-9912-faeec239fe34"))
    }

    fun removeImgList(position: Int) {
        _testImgList.value!!.removeAt(position)
    }
}