package com.beeeam.feed.feedCommentChange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beeeam.base.BaseViewModel
import com.beeeam.util.Const.UPLOADED
import com.beeeam.util.Const.UPLOADING
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedCommentChangeViewModel @Inject constructor(
    private val feedCommentChangeUseCase: RequestFeedCommentChangeUseCase,
) : BaseViewModel() {
    private val _uploadState = MutableLiveData(UPLOADING)
    val uploadState: LiveData<Int> = _uploadState

    fun changeComment(commentId: Int, content: String) {
        viewModelScope.launch {
            feedCommentChangeUseCase(commentId, content)
                .onSuccess {
                    _uploadState.value = UPLOADED
                }
                .onFailure {
                }
        }
    }
}
