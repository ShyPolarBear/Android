package com.beeeam.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beeeam.base.BaseViewModel
import com.beeeam.util.LOGIN_FAIL
import com.beeeam.util.SIGNUP_NEED
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.usecase.RequestLoginUseCase
import com.shypolarbear.domain.usecase.tokens.SetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetRefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: RequestLoginUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
) : BaseViewModel() {
    private val _tokens = MutableLiveData<String>()
    val tokens: LiveData<String> = _tokens
    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int> = _responseCode

    fun requestLogin(socialAccessToken: String) {
        viewModelScope.launch {
            val responseTokens = loginUseCase(LoginRequest(socialAccessToken))

            responseTokens.onSuccess { response ->
                setAccessTokenUseCase(response.data.accessToken)
                setRefreshTokenUseCase(response.data.refreshToken)
                setResponseCode(response.code)
            }

            responseTokens.onFailure { error ->
                if (error is HttpError) {
                    val errorBodyData = JSONObject(error.errorBody)
                    when (errorBodyData.get("code")) {
                        SIGNUP_NEED -> {
                            setResponseCode(SIGNUP_NEED)
                        }

                        LOGIN_FAIL -> {
                            setResponseCode(LOGIN_FAIL)
                            // token renew
                        }
                    }
                }
            }
        }
    }

    private fun setResponseCode(code: Int) {
        _responseCode.value = code
    }
}
