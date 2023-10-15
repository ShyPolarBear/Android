package com.shypolarbear.presentation.ui.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.presentation.databinding.ItemFeedCommentDeleteBinding
import com.shypolarbear.presentation.databinding.ItemFeedCommentLoadingBinding
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedCommentViewType
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailLikeBtnType
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentDeleteViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentLoadingViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentNormalViewHolder
import timber.log.Timber

class FeedCommentAdapter(
    private val onMyCommentPropertyClick: (view: ImageView, commentId: Int, position: Int, commentView: View ,content: String) -> Unit = { _, _, _, _, _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView, commentId: Int, position: Int, commentView: View) -> Unit = { _, _, _, _ -> },
    private val onMyReplyPropertyClick: (view: ImageView, commentId: Int, feedId: Int, content: String) -> Unit = { _, _, _, _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        commentId: Int,
        replyId: Int,
        itemType: FeedDetailLikeBtnType
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val onItemClick: (view: View) -> Unit = { _ -> }
): ListAdapter<Comment, RecyclerView.ViewHolder>(FeedCommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType) {
            FeedCommentViewType.LOADING.commentType -> {
                return FeedCommentLoadingViewHolder(ItemFeedCommentLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            FeedCommentViewType.NORMAL.commentType -> {
                return FeedCommentNormalViewHolder(
                    ItemFeedCommentNormalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onMyCommentPropertyClick = onMyCommentPropertyClick,
                    onOtherCommentPropertyClick = onOtherCommentPropertyClick,
                    onMyReplyPropertyClick = onMyReplyPropertyClick,
                    onOtherReplyPropertyClick = onOtherReplyPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onItemClick = onItemClick
                )
            }

            FeedCommentViewType.DELETE.commentType -> {
                return FeedCommentDeleteViewHolder(
                    ItemFeedCommentDeleteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when {
            getItem(position).commentId == 0 -> {
                (holder as FeedCommentLoadingViewHolder).bind(getItem(position))
            }
            getItem(position).isDeleted -> {
                (holder as FeedCommentDeleteViewHolder).bind(getItem(position))
            }
            !getItem(position).isDeleted -> {
                (holder as FeedCommentNormalViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position).commentId == 0 -> {
                FeedCommentViewType.LOADING.commentType
            }
            getItem(position).isDeleted -> {
                FeedCommentViewType.DELETE.commentType
            }
            else -> {
                FeedCommentViewType.NORMAL.commentType
            }
        }
    }
}

class FeedCommentDiffCallback : DiffUtil.ItemCallback<Comment>() {

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}