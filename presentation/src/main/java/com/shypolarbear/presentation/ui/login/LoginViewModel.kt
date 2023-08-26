package com.shypolarbear.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.Tokens
import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.usecase.LoginUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.LOGIN_FAIL
import com.shypolarbear.presentation.util.SIGNUP_NEED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {
    private val _tokens = MutableLiveData<Tokens>()
    val tokens: LiveData<Tokens> = _tokens
    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int> = _responseCode

    fun requestLogin(socialAccessToken: String) {
        viewModelScope.launch {
            val responseTokens = loginUseCase(LoginRequest(socialAccessToken))

            responseTokens.onSuccess { response ->
                setResponseCode(response.code)
                val tokens = Tokens(response.data.accessToken, response.data.refreshToken)
            }

            responseTokens.onFailure { error ->
                if (error is HttpError) {
                    val errorBodyData = JSONObject(error.errorBody)
                    when (errorBodyData.get("code")) {
                        SIGNUP_NEED -> {
                            setResponseCode(1006)
                        }

                        LOGIN_FAIL -> {
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