package com.shypolarbear.presentation.ui.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.presentation.databinding.ItemFeedCommentDeleteBinding
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedCommentViewType
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentDeleteViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentNormalViewHolder

class FeedCommentAdapter(
    private val onMyCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button, isLiked: Boolean, likeCnt: Int, textView: TextView) -> Int = { _, _, _, _ -> 0}
): ListAdapter<Comment, RecyclerView.ViewHolder>(FeedCommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType) {
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
                    onBtnLikeClick = onBtnLikeClick
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

        when(getItem(position)) {

            null -> {
                (holder as FeedCommentDeleteViewHolder).bind(getItem(position))
            }
            else -> {
                (holder as FeedCommentNormalViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comment: Comment? = getItem(position)
        // 추후에 isDeleted가 추가되면 이에 따라 판단할 예정
        return if (comment != null)
            FeedCommentViewType.NORMAL.commentType
        else
            FeedCommentViewType.DELETE.commentType
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