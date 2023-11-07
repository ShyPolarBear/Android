package com.shypolarbear.presentation.ui.more.changemyinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.more.Info
import com.shypolarbear.domain.usecase.image.RequestImageUploadUseCase
import com.shypolarbear.domain.usecase.more.RequestMyInfoChangeUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.domain.usecase.more.RequestCheckDuplicateNickNameUseCase
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
    private val imageUploadUseCase: RequestImageUploadUseCase,
    private val checkDuplicateNickNameUseCase: RequestCheckDuplicateNickNameUseCase
): BaseViewModel() {
    private val _myInfo = MutableLiveData<Info>()
    val myInfo: LiveData<Info> = _myInfo

    private val _uploadState = MutableLiveData(UPLOADING)
    val uploadState: LiveData<Int> = _uploadState

    private val _nickNameState = MutableLiveData(availableState.UNAVAILABLE)
    val nickNameState: LiveData<availableState> = _nickNameState


    fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess {
                    _myInfo.value = it.data
                }
                .onFailure {

                }

        }
    }

    fun requestChangeMyInfo(
        nickName: String,
        email: String,
        phoneNumber: String,
        profileImageFile: File?
    ) {
        viewModelScope.launch {
            // 프로필 사진 없는 경우
            when(profileImageFile) {
                null -> {
                    changeMyInfoUseCase(
                        nickName = nickName,
                        profileImage = null,
                        email = email,
                        phoneNumber = phoneNumber
                    )
                    _uploadState.value = UPLOADED
                }
                // 프로필 사진 있는 경우
                else -> {
                    imageUploadUseCase(ImageUploadRequest(ImageType.PROFILE.type, listOf(profileImageFile)))
                        .onSuccess {
                            changeMyInfoUseCase(nickName, it.data.imageLinks[0], email, phoneNumber)
                            _uploadState.value = UPLOADED
                        }
                        .onFailure {

                        }
                }
            }
        }
    }

    fun requestCheckNickName(nickName: String) {
        viewModelScope.launch {
            checkDuplicateNickNameUseCase(nickName)
                .onSuccess {
                    _nickNameState.value = availableState.AVAILABLE
                }
                .onFailure {
                    _nickNameState.value = availableState.UNAVAILABLE
                }
        }
    }
}