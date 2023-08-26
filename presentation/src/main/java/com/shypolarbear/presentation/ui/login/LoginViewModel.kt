package com.shypolarbear.presentation.ui.login

import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.domain.usecase.LoginUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    fun postLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            val responseTokens = loginUseCase(loginRequest)

            responseTokens.onSuccess {

            }
            responseTokens.onFailure { error ->
                if (error is HttpError) {
                    when (error.code) {
                        404 -> {

                        }

                        else -> {}
                    }
                }
            }
        }
    }

}