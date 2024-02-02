package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedChangeUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedWriteUseCase
import com.shypolarbear.domain.usecase.image.RequestImageUploadUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.ImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

const val UPLOADING = 0
const val UPLOADED = 1

@HiltViewModel
class FeedWriteViewModel @Inject constructor(
    private val feedDetailUseCase: LoadFeedDetailUseCase,
    private val feedChangeUseCase: RequestFeedChangeUseCase,
    private val feedWriteUseCase: RequestFeedWriteUseCase,
    private val imageUploadUseCase: RequestImageUploadUseCase,
) : BaseViewModel() {
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _selectedLiveImgList = MutableLiveData<MutableList<String>>(mutableListOf())
    val selectedLiveImgList: LiveData<MutableList<String>> = _selectedLiveImgList

    private val _uploadState = MutableLiveData(UPLOADING)
    val uploadState: LiveData<Int> = _uploadState

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            feedDetailUseCase(feedId)
                .onSuccess {
                    _feed.value = it.data
                }
                .onFailure {
                }
        }
    }

    fun changePost(feedId: Int, content: String, feedImages: List<String>?, title: String) {
        viewModelScope.launch {
            feedChangeUseCase(feedId, content, feedImages, title)
                .onSuccess {
                    _uploadState.value = UPLOADED
                }
                .onFailure {
                }
        }
    }

    fun changeImagePost(feedId: Int, content: String, originalImages: List<String>?, addedImageFiles: List<File>, title: String) {
        viewModelScope.launch {
            imageUploadUseCase(ImageUploadRequest(ImageType.FEED.type, addedImageFiles))
                .onSuccess {
                    _selectedLiveImgList.value = (originalImages!! + it.data.imageLinks).toMutableList()
                    feedChangeUseCase(feedId, content, _selectedLiveImgList.value, title)

                    _uploadState.value = UPLOADED
                }
                .onFailure {
                }
        }
    }

    fun writeNoImagePost(title: String, content: String) {
        viewModelScope.launch {
            feedWriteUseCase(title, content, listOf())
                .onSuccess {
                    _uploadState.value = UPLOADED
                }
                .onFailure {
                }
        }
    }

    fun writeImagePost(imageFiles: List<File>, title: String, content: String) {
        viewModelScope.launch {
            imageUploadUseCase(ImageUploadRequest(ImageType.FEED.type, imageFiles))
                .onSuccess {
                    _selectedLiveImgList.value = it.data.imageLinks.toMutableList()
                    feedWriteUseCase(title, content, _selectedLiveImgList.value)

                    _uploadState.value = UPLOADED
                }
                .onFailure {
                }
        }
    }

    fun addImgList(imgUri: List<Uri>) {
        val selectedImgList: MutableList<String> = _selectedLiveImgList.value!! // 기존에 선택된 이미지 리스트
        val selectedImgUriToStringList = imgUri.map { it.toString() }

        selectedImgList.addAll(0, selectedImgUriToStringList) // 기존에 선택된 이미지 리스트에 선택된 이미지 리스트 추가
        _selectedLiveImgList.value = selectedImgList // 선택된 이미지 리사이클러뷰 업데이트를 위한 라이브 데이터 설정
    }

    fun removeImgList(position: Int) {
        val selectedImgList: MutableList<String> = _selectedLiveImgList.value!! // 기존에 선택된 이미지 리스트

        selectedImgList.removeAt(position) // 기존의 선택된 이미지 리스트에서 position 위치의 요소 제거
        _selectedLiveImgList.value = selectedImgList // 선택된 이미지 리사이클러뷰 업데이트를 위한 라이브 데이터 설정
    }
}
