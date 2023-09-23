package com.shypolarbear.presentation.ui.more.changemyinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.more.Info
import com.shypolarbear.domain.usecase.image.RequestImageUploadUseCase
import com.shypolarbear.domain.usecase.more.RequestMyInfoChangeUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADED
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADING
import com.shypolarbear.presentation.util.ImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChangeMyInfoViewModel @Inject constructor(
    private val getMyInfoUseCase: LoadMyInfoUseCase,
    private val changeMyInfoUseCase: RequestMyInfoChangeUseCase,
    private val imageUploadUseCase: RequestImageUploadUseCase
): BaseViewModel() {
    private val _myInfo = MutableLiveData<Info>()
    val myInfo: LiveData<Info> = _myInfo

    private val _uploadImageList = MutableLiveData<List<String>>(listOf())
    val uploadImageList: LiveData<List<String>> = _uploadImageList

    private val _uploadState = MutableLiveData(UPLOADING)
    val uploadState: LiveData<Int> = _uploadState

    fun getMyInfo() {
        viewModelScope.launch {
            val info = getMyInfoUseCase()

            info
                .onSuccess {
                    _myInfo.value = it.data
                }
                .onFailure {

                }

        }
    }

//    fun requestChangeMyInfo(
//        nickName: String,
//        profileImage: String?,
//        email: String,
//        phoneNumber: String
//    ) {
//        viewModelScope.launch {
//            val response = changeMyInfoUseCase(nickName, profileImage, email, phoneNumber)
//
//            response
//                .onSuccess {
//                    _myInfo.value = it.data
//                }
//                .onFailure {
//
//                }
//
//        }
//    }

    fun requestChangeMyInfo(
        nickName: String,
        email: String,
        phoneNumber: String,
        profileImageFile: File
    ) {
        viewModelScope.launch {
            val uploadImages = imageUploadUseCase(ImageUploadRequest(ImageType.PROFILE.type, listOf(profileImageFile)))

            uploadImages
                .onSuccess {
                    _uploadImageList.value = it.data.imageLinks
                    changeMyInfoUseCase(nickName, _uploadImageList.value!![0], email, phoneNumber)

                    _uploadState.value = UPLOADED
                }
                .onFailure {

                }
        }
    }
}