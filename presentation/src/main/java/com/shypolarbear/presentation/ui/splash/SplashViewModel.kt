package com.shypolarbear.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.usecase.RequestTokenRenewUseCase
import com.shypolarbear.domain.usecase.tokens.GetRefreshTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetRefreshTokenUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val renewUseCase: RequestTokenRenewUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase
) : BaseViewModel() {

    private val _returnCode = MutableLiveData<Int>()
    val returnCode: LiveData<Int> = _returnCode

    init {
        renewToken()
    }
    private fun renewToken() {
        viewModelScope.launch {
            renewUseCase(loadRefreshTokenUseCase()).onSuccess { newTokens ->
                setAccessTokenUseCase(newTokens.data.accessToken)
                setRefreshTokenUseCase(newTokens.data.refreshToken)
                _returnCode.value = newTokens.code
            }.onFailure { error ->
                if (error is HttpError) {
                    _returnCode.value = error.code
                }
            }
        }
    }
}