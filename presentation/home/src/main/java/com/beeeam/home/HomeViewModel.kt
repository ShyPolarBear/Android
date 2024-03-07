package com.beeeam.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beeeam.base.BaseViewModel
import com.beeeam.util.Const
import com.beeeam.util.Event
import com.beeeam.util.simpleHttpErrorCheck
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.quiz.Quiz
import com.shypolarbear.domain.model.quiz.Review
import com.shypolarbear.domain.model.quiz.SolvedData
import com.shypolarbear.domain.usecase.feed.LoadFeedTotalUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.domain.usecase.quiz.QuizReviewUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSolvedUseCase
import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase,
    private val dailyQuizSolvedUseCase: QuizSolvedUseCase,
    private val reviewQuizUseCase: QuizReviewUseCase,
    private val getMyInfoUseCase: LoadMyInfoUseCase,
    private val feedTotalUseCase: LoadFeedTotalUseCase,
) : BaseViewModel() {

    private val _quizResponse = MutableLiveData<Event<Quiz>>()
    val quizResponse: LiveData<Event<Quiz>> = _quizResponse
    private val _reviewResponse = MutableLiveData<Event<Review>>()
    val reviewResponse: LiveData<Event<Review>> = _reviewResponse
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    private val _feed = MutableLiveData<List<Feed>>()
    val feed: LiveData<List<Feed>> = _feed
    private val _dailySubmit = MutableLiveData<Boolean>()
    val dailySubmit: LiveData<Boolean> = _dailySubmit
    private val _reviewQuizPage = MutableLiveData(0)
    val reviewQuizPage: LiveData<Int> = _reviewQuizPage
    private val _dailyQuizSolvedState = MutableLiveData<SolvedData>()

    fun loadFeedRecentData() {
        viewModelScope.launch {
            feedTotalUseCase("recent", lastFeedId = null)
                .onSuccess { response ->
                    if (response.data.content.isEmpty()) return@onSuccess

                    val imageContainList = mutableListOf<Feed>()
                    for (item in response.data.content) {
                        if (item.feedImages.isNotEmpty()) imageContainList.add(item)
                        if (imageContainList.size == Const.MAX_PAGES) break
                    }
                    if (imageContainList.size < Const.MAX_PAGES) {
                        loadFeedRecentMore(imageContainList, response.data.content.last().feedId)
                    }
                    _feed.value = imageContainList
                }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }

    private fun loadFeedRecentMore(imageContainList: MutableList<Feed>, lastFeedId: Int) {
        viewModelScope.launch {
            feedTotalUseCase("recent", lastFeedId).onSuccess { plusResponse ->
                for (item in plusResponse.data.content) {
                    if (item.feedImages.isNotEmpty()) imageContainList.add(item)
                    if (imageContainList.size == Const.MAX_PAGES) break
                }
            }.onFailure { error ->
                simpleHttpErrorCheck(error)
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

    fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess {
                    _userName.value = it.data.nickName
                }
        }
    }
}
