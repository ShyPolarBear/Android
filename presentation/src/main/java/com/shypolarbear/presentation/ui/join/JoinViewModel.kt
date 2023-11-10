package com.shypolarbear.presentation.ui.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.Tokens
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.join.JoinRequest
import com.shypolarbear.domain.usecase.RequestJoinUseCase
import com.shypolarbear.domain.usecase.image.RequestImageUploadUseCase
import com.shypolarbear.domain.usecase.tokens.SetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetRefreshTokenUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.ImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val joinUseCase: RequestJoinUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
    private val imageUploadUseCase: RequestImageUploadUseCase
) : BaseViewModel() {
    private val _termData = MutableLiveData<Boolean>()
    val termData: LiveData<Boolean> = _termData
    private val _nameData = MutableLiveData<String>()
    val nameData: LiveData<String> = _nameData
    private val _imageData = MutableLiveData<String>()
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

    fun requestImageUploadWithJoin(profileImage: List<File>){
        viewModelScope.launch {
            imageUploadUseCase.invoke(
                ImageUploadRequest(ImageType.PROFILE.type, profileImage)
            ).onSuccess { response ->
                _imageData.value = response.data.imageLinks.first()
            }
        }
    }

    fun requestJoin(socialAccessToken: String? = null) {
        viewModelScope.launch {
            val responseJoin = joinUseCase.invoke(
                JoinRequest(
                    socialAccessToken = socialAccessToken!!,
                    nickName = nameData.value!!,
                    phoneNumber = phoneData.value!!,
                    email = mailData.value!!,
                    profileImage = _imageData.value ?: ""
                )
            )

            responseJoin
                .onSuccess { response ->
                    setAccessTokenUseCase(response.data.accessToken)
                    setRefreshTokenUseCase(response.data.refreshToken)

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