package com.shypolarbear.presentation.ui.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.shypolarbear.presentation.databinding.ItemFeedReplyDeleteBinding
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedCommentViewType
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailLikeBtnType
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedReplyDeleteViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedReplyNormalViewHolder

class FeedReplyAdapter (
    private val onMyReplyPropertyClick: (view: ImageView, commentId: Int, position: Int) -> Unit = { _, _, _ -> },
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
    private val parentCommentId: Int
): ListAdapter<ChildComment, RecyclerView.ViewHolder>(FeedReplyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType) {
            FeedCommentViewType.NORMAL.commentType -> {
                return FeedReplyNormalViewHolder(
                    ItemFeedReplyNormalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onMyReplyPropertyClick = onMyReplyPropertyClick,
                    onOtherReplyPropertyClick = onOtherReplyPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    parentCommentId = parentCommentId
                )
            }

            FeedCommentViewType.DELETE.commentType -> {
                return FeedReplyDeleteViewHolder(
                    ItemFeedReplyDeleteBinding.inflate(
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
                (holder as FeedReplyDeleteViewHolder).bind(getItem(position))
            }
            else -> {
                (holder as FeedReplyNormalViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val reply: ChildComment? = getItem(position)
        // 추후에 isDeleted가 추가되면 이에 따라 판단할 예정
        return if (reply != null)
            FeedCommentViewType.NORMAL.commentType
        else
            FeedCommentViewType.DELETE.commentType
    }
}

class FeedReplyDiffCallback : DiffUtil.ItemCallback<ChildComment>() {

    override fun areItemsTheSame(oldItem: ChildComment, newItem: ChildComment): Boolean {
        return oldItem.commentId == newItem.commentId
    }

    override fun areContentsTheSame(oldItem: ChildComment, newItem: ChildComment): Boolean {
        return oldItem == newItem
    }
}