package com.shypolarbear.presentation.ui.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.Tokens
import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.usecase.JoinUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
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
    private val _tokens = MutableLiveData<Tokens>()
    val tokens: LiveData<Tokens> = _tokens
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _pageState = arrayListOf(false, false, false, false)
    val pageState: List<Boolean> = _pageState
    private val _pageIndex = MutableLiveData<Int>(1)
    val pageIndex: LiveData<Int> = _pageIndex

    fun requestJoin(socialAccessToken: String? = null) {
        viewModelScope.launch {
            val responseJoin = joinUseCase.invoke(
                JoinRequest(
                    socialAccessToken!!,
                    nickName = nameData.value!!,
                    phoneNumber = phoneData.value!!,
                    email = mailData.value!!,
                    profileImage = "아직 미구현"
                )
            )

            responseJoin
                .onSuccess { response ->
                    initToken(Tokens(response.data.accessToken, response.data.refreshToken))
                }
                .onFailure {error ->
                    if (error is HttpError) {
                        val errorBodyData = JSONObject(error.errorBody)
                        when(errorBodyData.get("code")){
                            1101, 1004 ->{
                                setErrorMessage(errorBodyData.get("message") as String)
                            }
                            1007 ->{}
                        }
                    }
                }
        }

    }

    private fun initToken(responseToken: Tokens) {
        _tokens.value = responseToken
    }

    private fun setErrorMessage(message: String){
        _errorMessage.value = message
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