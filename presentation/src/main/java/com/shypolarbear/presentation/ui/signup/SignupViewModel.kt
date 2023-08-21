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
import timber.log.Timber
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
    private val _tokens = MutableLiveData<Token>()
    val tokens: LiveData<Token> = _tokens

    private val _pageState = arrayListOf(false, false, false, false)
    val pageState: List<Boolean> = _pageState
    private val _pageIndex = MutableLiveData<Int>(1)
    val pageIndex: LiveData<Int> = _pageIndex


    fun requestJoin(socialAccessToken: String) {
        viewModelScope.launch {
            val responseJoin = joinUseCase.invoke(
                JoinRequest(
                    socialAccessToken,
                    nickName = nameData.value.toString(),
                    phoneNumber = phoneData.value.toString(),
                    email = mailData.value.toString(),
                    profileImage = "아직 미구현"
                )
            )

            responseJoin
                .onSuccess { response ->
                    when (response.code.toInt()) {
                        0 -> {
                            Timber.tag("JOIN").i("카카오톡으로 로그인 성공 " + response.data[0]+ response.data[1])
                            initToken(Token(response.data[0], response.data[1]))
                        }

                        1007 -> {
                            Timber.tag("JOIN").i(response.message)

                        }

                        1101 -> {
                            Timber.tag("JOIN").i(response.message)

                        }

                        1004 -> {
                            Timber.tag("JOIN").i(response.message)

                        }
                    }
                }
                .onFailure {response ->
                    Timber.tag("JOIN").i(response.message)
                }
        }

    }

    private fun initToken(responseToken: Token) {
        _tokens.value = responseToken
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