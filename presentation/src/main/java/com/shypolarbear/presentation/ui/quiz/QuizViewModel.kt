package com.shypolarbear.presentation.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.quiz.Quiz
import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase,
    private val accessTokenUseCase: GetAccessTokenUseCase,
) : BaseViewModel() {

    private val _tokens = MutableLiveData<String?>()
    val tokens: LiveData<String?> = _tokens
    private val _quizResponse = MutableLiveData<Quiz>()
    val quizResponse: LiveData<Quiz> = _quizResponse

    fun getAccessToken() {
        viewModelScope.launch {
            _tokens.value = accessTokenUseCase()
        }
    }

    fun requestQuiz() {
        viewModelScope.launch {
            val responseQuiz = quizUseCase()

            responseQuiz.onSuccess { response ->
                _quizResponse.value = response.data
                Timber.tag("QUIZ").d("${_quizResponse.value}")
            }
                .onFailure { error ->
                    Timber.tag("ERROR").d("${error}")

                    if (error is HttpError) {
                        val errorBodyData = JSONObject(error.errorBody)
                        Timber.tag("ERROR").d("${errorBodyData.get("code")}")
                    }
                }
        }
    }
}