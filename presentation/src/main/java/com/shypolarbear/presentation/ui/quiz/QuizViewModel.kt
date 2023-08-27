package com.shypolarbear.presentation.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.usecase.AccessTokenUseCase
import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase,
    private val accessTokenUseCase: AccessTokenUseCase
): BaseViewModel() {

    private val _tokens = MutableLiveData<String>()
    val tokens: LiveData<String> = _tokens
    fun getAccessToken(){
        viewModelScope.launch {
            _tokens.value = accessTokenUseCase.getAccessToken()
        }
    }
}