package com.shypolarbear.presentation.ui.more.changemyinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.more.Info
import com.shypolarbear.domain.usecase.more.RequestMyInfoChangeUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeMyInfoViewModel @Inject constructor(
    private val getMyInfoUseCase: LoadMyInfoUseCase,
    private val changeMyInfoUseCase: RequestMyInfoChangeUseCase
): BaseViewModel() {
    private val _myInfo = MutableLiveData<Info>()
    val myInfo: LiveData<Info> = _myInfo

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

    fun requestChangeMyInfo(
        nickName: String,
        profileImage: String?,
        email: String,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            val response = changeMyInfoUseCase(nickName, profileImage, email, phoneNumber)

            response
                .onSuccess {
                    _myInfo.value = it.data
                }
                .onFailure {

                }

        }
    }
}