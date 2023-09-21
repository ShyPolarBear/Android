package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.usecase.feed.RequestFeedChangeUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedWriteUseCase
import com.shypolarbear.domain.usecase.image.RequestImageUploadUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

const val UPLOADING = 0
const val UPLOADED = 1

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val feedDetailUseCase: LoadFeedDetailUseCase,
    private val changePostUseCase: RequestFeedChangeUseCase,
    private val feedWriteUseCase: RequestFeedWriteUseCase,
    private val imageUploadUseCase: RequestImageUploadUseCase
): BaseViewModel(){
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _selectedLiveImgList = MutableLiveData<MutableList<FeedWriteImg>>(mutableListOf())
    val selectedLiveImgList: LiveData<MutableList<FeedWriteImg>> = _selectedLiveImgList

    private val _uploadImageList = MutableLiveData<List<String>>()
    val uploadImageList: LiveData<List<String>> = _uploadImageList

    private val _uploadState = MutableLiveData<Int>(UPLOADING)
    val uploadState: LiveData<Int> = _uploadState

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
            val feedChangeResult = changePostUseCase(feedId, content, feedImages, title)

            feedChangeResult
                .onSuccess {
                    _uploadState.value = UPLOADED
                }
                .onFailure {

                }
        }
    }

    fun writeNoImagePost(title: String, content: String) {
        viewModelScope.launch {
            val feedWriteResult = feedWriteUseCase(title, content, listOf())

            feedWriteResult
                .onSuccess {
                    _uploadState.value = UPLOADED
                }
                .onFailure {

                }
        }

    }

    fun writeImagePost(imageType: String, imageFiles: List<File>, title: String, content: String) {
        viewModelScope.launch {
            val uploadImages = imageUploadUseCase(ImageUploadRequest(imageType, imageFiles))

            uploadImages
                .onSuccess {
                    _uploadImageList.value = it.data.imageLinks
                    feedWriteUseCase(title, content, _uploadImageList.value)

                    _uploadState.value = UPLOADED
                }
                .onFailure {

                }
        }
    }

    fun addImgList(imgUri: List<Uri>) {
        val selectedImgList: MutableList<FeedWriteImg> = _selectedLiveImgList.value!!
        val imgUriToStringList = imgUri.map { FeedWriteImg(it.toString()) }

        selectedImgList.addAll(0, imgUriToStringList)
        _selectedLiveImgList.value = selectedImgList
    }

    fun removeImgList(position: Int) {
        val selectedImgList: MutableList<FeedWriteImg> = _selectedLiveImgList.value!!
        val imgUploadList: MutableList<String> = _uploadImageList.value!!.toMutableList()

        imgUploadList.removeAt(position)
        selectedImgList.removeAt(position)

        _uploadImageList.value = imgUploadList
        _selectedLiveImgList.value = selectedImgList
    }

    fun setUploadImageList(images: List<String>) {
        _uploadImageList.value = images
    }
}