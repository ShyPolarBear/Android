package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.usecase.feed.LoadCommentUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedDeleteUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedLikeUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val feedDetailUseCase: LoadFeedDetailUseCase,
    private val feedCommentUseCase: LoadCommentUseCase,
    private val feedDeleteUseCase: RequestFeedDeleteUseCase,
    private val feedLikeUseCase: RequestFeedLikeUseCase
): BaseViewModel() {

    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _feedComment = MutableLiveData<List<Comment>>()
    val feedComment: LiveData<List<Comment>> = _feedComment

    private lateinit var currentCommentList: List<Comment>

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
            val feedDetailTestData = feedDetailUseCase(feedId)

            feedDetailTestData
                .onSuccess {
                    _feed.value = it.data
                }
                .onFailure {

                }
        }
    }

    fun loadFeedComment(feedId: Int) {
        viewModelScope.launch {
            val feedCommentMockData = feedCommentUseCase(feedId)

            feedCommentMockData
                .onSuccess {
                    _feedComment.value = it.data.comments
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
        _feedComment.value = updatedCommentList
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
        _feedComment.value = updatedCommentList
    }

    fun requestDeleteFeed(feedId: Int) {
        viewModelScope.launch {
            feedDeleteUseCase(feedId)
        }
    }
}