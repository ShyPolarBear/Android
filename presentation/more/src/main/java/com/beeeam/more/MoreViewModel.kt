package com.beeeam.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beeeam.base.BaseViewModel
import com.beeeam.util.LoginState
import com.shypolarbear.domain.model.more.Info
import com.shypolarbear.domain.usecase.RequestLogoutUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getMyInfoUseCase: LoadMyInfoUseCase,
    private val requestLogoutUseCase: RequestLogoutUseCase,
) : BaseViewModel() {
    private val _myInfo = MutableLiveData<Info>()
    val myInfo: LiveData<Info> = _myInfo

    private val _loginState = MutableLiveData(LoginState.LOGIN)
    val loginState: LiveData<LoginState> = _loginState

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

    fun requestLogout() {
        viewModelScope.launch {
            requestLogoutUseCase()
                .onSuccess {
                    _loginState.value = LoginState.LOGOUT
                }
                .onFailure {
                }
        }
    }
}
