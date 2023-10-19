package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.domain.model.more.Info
import com.shypolarbear.domain.usecase.feed.LoadFeedCommentUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentDeleteUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentLikeUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentWriteUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedDeleteUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedLikeUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FeedDetailViewModel @Inject constructor(
    private val feedDetailUseCase: LoadFeedDetailUseCase,
    private val feedCommentUseCase: LoadFeedCommentUseCase,
    private val feedDeleteUseCase: RequestFeedDeleteUseCase,
    private val feedLikeUseCase: RequestFeedLikeUseCase,
    private val feedCommentWriteUseCase: RequestFeedCommentWriteUseCase,
    private val feedCommentLikeUseCase: RequestFeedCommentLikeUseCase,
    private val feedCommentDeleteUseCase: RequestFeedCommentDeleteUseCase,
    private val getMyInfoUseCase: LoadMyInfoUseCase,
): BaseViewModel() {

    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _feedComment = MutableLiveData<List<Comment>>()
    val feedComment: LiveData<List<Comment>> = _feedComment

    private lateinit var currentCommentList: List<Comment>
    private lateinit var myInfo: Info

    var commentIsLast = false
    var commentLoadType = CommentLoadType.INIT

    fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { myInfo = it.data }
                .onFailure {  }

        }
    }

    fun loadFeedDetail(feedId: Int) {
        viewModelScope.launch {
           feedDetailUseCase(feedId)
                .onSuccess {
                    _feed.value = it.data
                }
                .onFailure {

                }
        }
    }

    fun loadFeedComment(feedId: Int, commentLoadType: CommentLoadType) {
        var feedCommentData: Result<FeedComment>

        viewModelScope.launch {

            feedCommentData = when {
                _feedComment.value.isNullOrEmpty() || commentLoadType == CommentLoadType.INIT -> { feedCommentUseCase(feedId, null) }
                else -> { feedCommentUseCase(feedId, _feedComment.value!![_feedComment.value!!.lastIndex - 1].commentId) }
            }

            feedCommentData
                .onSuccess {
                    val newDataList = it.data.content
                    val currentList = _feedComment.value ?: emptyList()

                    if (!currentList.isNullOrEmpty()) {
                        val removeProgressList = currentList.toMutableList()
                        removeProgressList.removeLast()

                        _feedComment.value = removeProgressList
                    }

                    commentIsLast = it.data.last

                    when {
                        commentLoadType == CommentLoadType.INIT -> { _feedComment.value = newDataList }
                        commentIsLast -> { _feedComment.value = currentList + newDataList }
                        else-> { _feedComment.value = currentList + newDataList + listOf(Comment()) }
                    }
                }
                .onFailure {  }
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
            feedCommentLikeUseCase(commentId)
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
           feedCommentLikeUseCase(replyId)
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

    fun requestWriteFeedComment(feedId: Int, content: String, timeFormat: String) {
        val feedCommentList: MutableList<Comment> = mutableListOf()

        feedCommentList.addAll(0, _feedComment.value!!)

        val realTime = System.currentTimeMillis()
        val commentCreatedDateFormat = SimpleDateFormat(timeFormat, Locale.US)

        viewModelScope.launch {
            feedCommentWriteUseCase(feedId, null, content)
                .onSuccess {
                    feedCommentList.add(Comment(
                        commentId = it.data.commentId,
                        authorNickname = myInfo.nickName,
                        authorProfileImage = myInfo.profileImage,
                        content = content,
                        createdDate = commentCreatedDateFormat.format(realTime)
                    ))
                    _feedComment.value = feedCommentList
                }
                .onFailure {

                }
        }
    }

    fun requestDeleteFeedComment(commentId: Int, position: Int) {
        val feedCommentList: MutableList<Comment> = mutableListOf()
        var feedCommentReply: List<ChildComment>
        feedCommentList.addAll(0, _feedComment.value!!)

        viewModelScope.launch {
            feedCommentDeleteUseCase(commentId)
                .onSuccess {
                    feedCommentReply = feedCommentList[position].childComments
                    feedCommentList[position] =
                        Comment(isDeleted = true, childComments = feedCommentReply)
                    _feedComment.value = feedCommentList
                }
                .onFailure {  }
        }
    }

    fun requestWriteFeedReply(feedId: Int, parentId: Int, content: String, timeFormat: String, position: Int) {
        val feedCommentList: MutableList<Comment> = mutableListOf()
        val feedReplyList: MutableList<ChildComment> = mutableListOf()

        feedCommentList.addAll(0, _feedComment.value!!)
        feedReplyList.addAll(_feedComment.value!![position].childComments)

        val realTime = System.currentTimeMillis()
        val commentCreatedDateFormat = SimpleDateFormat(timeFormat, Locale.US)

        viewModelScope.launch {
            feedCommentWriteUseCase(feedId, parentId, content)

            // 추후 아이템만 추가하는 방식으로 변경할 예정
            loadFeedComment(feedId, CommentLoadType.INIT)
        }

//        feedReplyList.add(ChildComment(
//            commentId = 0,
//            authorNickname = myInfo.nickName,
//            authorProfileImage = myInfo.profileImage,
//            content = content,
//            createdDate = commentCreatedDateFormat.format(realTime)
//        ))
//        feedCommentList[position].childComments = feedReplyList
//        _feedComment.value = feedCommentList

    }

    fun requestDeleteFeedReply(commentId: Int, feedId: Int) {
        val feedCommentList: MutableList<Comment> = mutableListOf()
        feedCommentList.addAll(0, _feedComment.value!!)

        viewModelScope.launch {
            feedCommentDeleteUseCase(commentId)

            // 추후 아이템의 상태만 변경하는 방식으로 수정할 예정
            loadFeedComment(feedId, CommentLoadType.INIT)
        }
    }
}