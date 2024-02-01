package com.shypolarbear.presentation.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.mypage.MyComment
import com.shypolarbear.domain.model.mypage.MyCommentRequest
import com.shypolarbear.domain.model.mypage.MyPost
import com.shypolarbear.domain.model.mypage.MyPostRequest
import com.shypolarbear.domain.usecase.feed.RequestFeedDeleteUseCase
import com.shypolarbear.domain.usecase.mypage.LoadMyCommentUseCase
import com.shypolarbear.domain.usecase.mypage.LoadMyPostUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.simpleHttpErrorCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val loadMyPostUseCase: LoadMyPostUseCase,
    private val loadMyCommentUseCase: LoadMyCommentUseCase,
    private val feedDeleteUseCase: RequestFeedDeleteUseCase,

) : BaseViewModel() {

    private val _myPostResponse = MutableLiveData<MyPost>()
    val myPostResponse: LiveData<MyPost> = _myPostResponse
    private val _myCommentResponse = MutableLiveData<MyComment>()
    val myCommentResponse: LiveData<MyComment> = _myCommentResponse

    fun loadMyFeed() {
        loadMyPost()
        loadMyComment()
    }

    private fun loadMyPost(lastFeedId: Int? = null): Job {
        val loadJob = viewModelScope.launch {
            val responseMyPost =
                loadMyPostUseCase(getMyPostRequest = MyPostRequest(lastFeedId, null))
            Timber.tag("MY_PAGE").d("$responseMyPost")

            responseMyPost.onSuccess { response ->
                _myPostResponse.value = response.data
                Timber.tag("MY_PAGE").d("${_myPostResponse.value}")
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
        return loadJob
    }

    fun loadMoreMyPost(contentType: FeedContentType) {
        viewModelScope.launch {
            when (contentType) {
                FeedContentType.POST -> {
                    if (!myPostResponse.value!!.last) {
                        val loadJob = loadMyPost(
                            myPostResponse.value!!.content.last().feedId,
                        )
                        loadJob.join()
                    } else {
                        // isLast = true
                    }
                }

                FeedContentType.COMMENT -> {
                    if (!myCommentResponse.value!!.last) {
                        val loadJob = loadMyComment(
                            myCommentResponse.value!!.content.last().commentId,
                        )
                        loadJob.join()
                    } else {
                        // isLast = true
                    }
                }
            }
        }
    }

    private fun loadMyComment(lastCommentId: Int? = null): Job {
        val loadJob = viewModelScope.launch {
            val responseMyComment =
                loadMyCommentUseCase(getMyCommentRequest = MyCommentRequest(lastCommentId, null))
            Timber.tag("MY_PAGE").d("$responseMyComment")

            responseMyComment.onSuccess { response ->
                _myCommentResponse.value = response.data
                Timber.tag("MY_PAGE").d("${_myCommentResponse.value}")
            }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
        return loadJob
    }

    fun requestDeleteFeed(feedId: Int) {
        viewModelScope.launch {
            feedDeleteUseCase(feedId)
                .onSuccess {
                    loadMyPost(myPostResponse.value!!.content.last().feedId)
                }.onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }
}
