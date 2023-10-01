package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.usecase.feed.LoadCommentUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedDeleteUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentLikeUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentWriteUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedLikeUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val feedDetailUseCase: LoadFeedDetailUseCase,
    private val feedCommentUseCase: LoadCommentUseCase,
    private val feedDeleteUseCase: RequestFeedDeleteUseCase,
    private val feedLikeUseCase: RequestFeedLikeUseCase,
    private val feedCommentWriteUseCase: RequestFeedCommentWriteUseCase,
    private val feedCommentLikeUseCase: RequestFeedCommentLikeUseCase
): BaseViewModel() {

    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _feedComment = MutableLiveData<List<Comment>>()
    val feedComment: LiveData<List<Comment>> = _feedComment

    private lateinit var currentCommentList: List<Comment>

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            val feedDetailData = feedDetailUseCase(feedId)

            feedDetailData
                .onSuccess {
                    _feed.value = it.data
                }
                .onFailure {

                }
        }
    }

    fun loadFeedComment(feedId: Int) {
        viewModelScope.launch {
            val feedCommentData = feedCommentUseCase(feedId)

            feedCommentData
                .onSuccess {
                    _feedComment.value = it.data.content
                }
                .onFailure {

                }
        }
    }

    fun clickFeedPostLikeBtn(isLiked: Boolean, likeCnt: Int, feedId: Int) {
        val currentFeed = _feed.value?: return
        val updatedFeed = currentFeed.copy(isLike = isLiked, likeCount = likeCnt)

        viewModelScope.launch { feedLikeUseCase(feedId) }
        _feed.value = updatedFeed
    }

    fun clickCommentLikeBtn(isLiked: Boolean, likeCnt: Int, commentId: Int) {

        currentCommentList = _feedComment.value!!

        val updatedCommentList = currentCommentList.map { comment ->
            if (comment.commentId == commentId)
                comment.copy(isLike = isLiked, likeCount = likeCnt)
            else
                comment
        }
        viewModelScope.launch {
            val result = feedCommentLikeUseCase(commentId)

            result
                .onSuccess {
                    _feedComment.value = updatedCommentList
                }
                .onFailure {  }
        }
    }

    fun clickReplyLikeBtn(isLiked: Boolean, likeCnt: Int, parentCommentId: Int, replyId: Int) {

        currentCommentList = _feedComment.value!!

        val updatedCommentList = currentCommentList.map { comment ->
            if (comment.commentId == parentCommentId) {

                val updatedReplyList = comment.childComments.map { reply ->
                    if (reply.commentId == replyId)
                        reply.copy(isLike = isLiked, likeCount = likeCnt)
                    else
                        reply
                }
                comment.copy(childComments = updatedReplyList)
            }
            else
                comment
        }
        viewModelScope.launch {
            val result = feedCommentLikeUseCase(replyId)

            result
                .onSuccess {
                    _feedComment.value = updatedCommentList
                }
                .onFailure {  }
        }
    }

    fun requestDeleteFeed(feedId: Int) {
        viewModelScope.launch {
            feedDeleteUseCase(feedId)
        }
    }

    fun requestFeedCommentWrite(feedId: Int, parentId: Int?, content: String) {
        viewModelScope.launch {
            val feedCommentWriteResult = feedCommentWriteUseCase(feedId, parentId, content)

            feedCommentWriteResult
                .onSuccess {
                    loadFeedComment(feedId)
                }
                .onFailure {

                }
        }

    }
}