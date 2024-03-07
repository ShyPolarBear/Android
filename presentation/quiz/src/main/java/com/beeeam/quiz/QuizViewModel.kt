package com.beeeam.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beeeam.base.BaseViewModel
import com.beeeam.util.Event
import com.beeeam.util.QuizType
import com.beeeam.util.simpleHttpErrorCheck
import com.shypolarbear.domain.model.quiz.Correction
import com.shypolarbear.domain.model.quiz.Quiz
import com.shypolarbear.domain.model.quiz.Review
import com.shypolarbear.domain.model.quiz.SubmitRequestMulti
import com.shypolarbear.domain.model.quiz.SubmitRequestOX
import com.shypolarbear.domain.usecase.quiz.QuizSubmitMultiUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitOXUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val submitOXUseCase: QuizSubmitOXUseCase,
    private val submitMultiUseCase: QuizSubmitMultiUseCase,
) : BaseViewModel() {

    private val _quizResponse = MutableLiveData<Event<Quiz>>()
    val quizResponse: LiveData<Event<Quiz>> = _quizResponse
    private val _reviewResponse = MutableLiveData<Event<Review>>()
    val reviewResponse: LiveData<Event<Review>> = _reviewResponse
    private val _answerId = MutableLiveData<String?>()
    val answerId: LiveData<String?> = _answerId
    private val _submitResponse = MutableLiveData<Event<Correction>>()
    val submitResponse: LiveData<Event<Correction>> = _submitResponse
    private val _dailySubmit = MutableLiveData<Boolean>()
    val dailySubmit: LiveData<Boolean> = _dailySubmit
    private val _reviewQuizPage = MutableLiveData(0)
    val reviewQuizPage: LiveData<Int> = _reviewQuizPage
    private val _quizInstance = MutableLiveData<Quiz>()
    val quizInstance: LiveData<Quiz> = _quizInstance

    fun getQuizInstance() {
        _quizInstance.value = when (_dailySubmit.value) {
            false -> {
                _quizResponse.value!!.peekContent()
            }

            else -> {
                _reviewResponse.value!!.peekContent().content[_reviewQuizPage.value!!]
            }
        }
    }

    fun submitAnswer(isTimeOut: Boolean = false) {
        val type =
            if (_quizInstance.value!!.type == QuizType.MULTI.type) QuizType.MULTI else QuizType.OX
        viewModelScope.launch {
            val responseAnswer = when (type) {
                QuizType.MULTI -> {
                    submitMultiUseCase(
                        _quizInstance.value!!.quizId,
                        SubmitRequestMulti(isTimeOut, _answerId.value?.toLong()),
                    )
                }

                QuizType.OX -> {
                    submitOXUseCase(
                        _quizInstance.value!!.quizId,
                        SubmitRequestOX(isTimeOut, _answerId.value?.toString()),
                    )
                }
            }

            responseAnswer.onSuccess { response ->
                _submitResponse.value = Event(response.data)
            }.onFailure { error ->
                simpleHttpErrorCheck(error)
            }
        }
    }

    fun goNextPage() {
        _reviewQuizPage.value = _reviewQuizPage.value?.plus(1)
    }

    fun setAnswer(answer: String) {
        _answerId.value = answer
    }

    fun initAnswer() {
        if (!_answerId.value.isNullOrEmpty()) {
            _answerId.value = null
        }
    }
}
