package com.shypolarbear.presentation.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.quiz.Correction
import com.shypolarbear.domain.model.quiz.Quiz
import com.shypolarbear.domain.model.quiz.Review
import com.shypolarbear.domain.model.quiz.SolvedData
import com.shypolarbear.domain.model.quiz.SubmitRequestMulti
import com.shypolarbear.domain.model.quiz.SubmitRequestOX
import com.shypolarbear.domain.usecase.feed.LoadFeedTotalUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.domain.usecase.quiz.QuizReviewUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSolvedUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitMultiUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitOXUseCase
import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.ui.quiz.main.MAX_PAGES
import com.shypolarbear.presentation.util.Event
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.simpleHttpErrorCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase,
    private val dailyQuizSolvedUseCase: QuizSolvedUseCase,
    private val reviewQuizUseCase: QuizReviewUseCase,
    private val submitOXUseCase: QuizSubmitOXUseCase,
    private val submitMultiUseCase: QuizSubmitMultiUseCase,
    private val getMyInfoUseCase: LoadMyInfoUseCase,
    private val feedTotalUseCase: LoadFeedTotalUseCase,
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
    private val _dailyQuizSolvedState = MutableLiveData<SolvedData>()
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed

    fun loadFeedRecentData() {
        viewModelScope.launch {
            feedTotalUseCase("recent", lastFeedId = null)
                .onSuccess { response ->
                    val imageContainList = mutableListOf<Feed>()
                    for(item in response.data.content){
                        if(item.feedImages.isNotEmpty()) imageContainList.add(item)
                        if(imageContainList.size == MAX_PAGES) break
                    }
                    _feed.value = imageContainList
                }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }

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

    fun requestQuiz() {
        viewModelScope.launch {
            val responseQuiz = quizUseCase()

            responseQuiz.onSuccess { response ->
                _quizResponse.value = Event(response.data)
                Timber.tag("QUIZ").d("${_quizResponse.value}")
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }


    fun requestReviewQuiz() {
        viewModelScope.launch {
            val responseQuiz = reviewQuizUseCase()
            _reviewQuizPage.value = 0
            responseQuiz.onSuccess { response ->
                _reviewResponse.value = Event(response.data)
                Timber.tag("REVIEW").d("${_reviewResponse.value}")
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }

    fun requestDailyQuizSolvedState() {
        viewModelScope.launch {
            val responseState = dailyQuizSolvedUseCase()

            responseState.onSuccess { response ->
                _dailyQuizSolvedState.value = response.data
                _dailySubmit.value = when (_dailyQuizSolvedState.value!!.quizId) {
                    null -> false
                    else -> true
                }
            }.onFailure { error ->
                simpleHttpErrorCheck(error)
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
                        SubmitRequestMulti(isTimeOut, _answerId.value?.toLong())
                    )
                }

                QuizType.OX -> {
                    submitOXUseCase(
                        _quizInstance.value!!.quizId,
                        SubmitRequestOX(isTimeOut, _answerId.value?.toString())
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

    fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess {
                    _userName.value = it.data.nickName
                }
                .onFailure {

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