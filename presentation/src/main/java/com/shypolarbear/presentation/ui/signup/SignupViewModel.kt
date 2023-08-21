package com.shypolarbear.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.model.join.Token
import com.shypolarbear.domain.usecase.JoinUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val joinUseCase: JoinUseCase,
) : BaseViewModel() {
    private val _termData = MutableLiveData<Boolean>()
    val termData: LiveData<Boolean> = _termData
    private val _nameData = MutableLiveData<String>()
    val nameData: LiveData<String> = _nameData
    private val _phoneData = MutableLiveData<String>()
    val phoneData: LiveData<String> = _phoneData
    private val _mailData = MutableLiveData<String>()
    val mailData: LiveData<String> = _mailData

    private val _pageState = arrayListOf(false, false, false, false)
    val pageState: List<Boolean> = _pageState
    private val _pageIndex = MutableLiveData<Int>(1)
    val pageIndex: LiveData<Int> = _pageIndex

    fun requestJoin(socialAccessToken: String) {
        viewModelScope.launch {
            val responseJoin = joinUseCase.invoke(
                JoinRequest(
                    socialAccessToken,
                    nickName = nameData.toString(),
                    phoneNumber = phoneData.toString(),
                    email = mailData.toString(),
                    profileImage = ""
                )
            )

            responseJoin
                .onSuccess {
                    when(it.code){
                        0 -> {
                            val token = Token(it.data[0], it.data[1])
                            // DataStore에 넣기
                        }
                        1007 -> {

                        }
                        1101 -> {

                        }
                        1004 -> {

                        }
                    }
                }
                .onFailure {

                }
        }

    }

    fun setPageState(page: Int, state: Boolean) {
        _pageState[page] = state
    }

    fun goBackPageIndex() {
        _pageIndex.value = _pageIndex.value!! - 1
    }

    fun goNextPageIndex() {
        _pageIndex.value = _pageIndex.value!! + 1
    }

    fun getActualPageIndex(): Int {
        return _pageIndex.value!! - 1
    }

    fun setTermData(newData: Boolean) {
        _termData.value = newData
    }

    fun setNameData(newData: String) {
        _nameData.value = newData
    }

    fun setPhoneData(newData: String) {
        _phoneData.value = newData
    }

    fun setMailData(newData: String) {
        _mailData.value = newData
    }
}