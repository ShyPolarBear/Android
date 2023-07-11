package com.shypolarbear.presentation.ui.feed.feedDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedCommentDeleteBinding
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.databinding.ItemFeedReplyDeleteBinding
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedCommentViewType
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentDeleteViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentNormalViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedReplyDeleteViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedReplyNormalViewHolder
import timber.log.Timber

class FeedCommentAdapter(
    private val viewLifeCycleOwner: LifecycleOwner
): ListAdapter<FeedComment, RecyclerView.ViewHolder>(FeedCommentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.d("viewType: $viewType")

        when(viewType) {
            FeedCommentViewType.COMMENT_NORMAL -> {
                return FeedCommentNormalViewHolder(
                    ItemFeedCommentNormalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), viewLifeCycleOwner
                )
            }

            FeedCommentViewType.COMMENT_DELETE -> {
                return FeedCommentDeleteViewHolder(
                    ItemFeedCommentDeleteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            FeedCommentViewType.REPLY_NORMAL -> {
                return FeedReplyNormalViewHolder(
                    ItemFeedReplyNormalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), viewLifeCycleOwner
                )
            }

            FeedCommentViewType.REPLY_DELETE -> {
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

        when(getItem(position).viewType) {
            FeedCommentViewType.COMMENT_NORMAL -> {
                (holder as FeedCommentNormalViewHolder).bind(getItem(position))
            }

            FeedCommentViewType.COMMENT_DELETE -> {
                (holder as FeedCommentDeleteViewHolder).bind(getItem(position))
            }

            FeedCommentViewType.REPLY_NORMAL -> {
                (holder as FeedReplyNormalViewHolder).bind(getItem(position))
            }

            FeedCommentViewType.REPLY_DELETE -> {
                (holder as FeedReplyDeleteViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comment = getItem(position)
        return comment.viewType
    }
}

class FeedCommentDiffCallback : DiffUtil.ItemCallback<FeedComment>() {

    override fun areItemsTheSame(oldItem: FeedComment, newItem: FeedComment): Boolean {
        return oldItem.test1 == newItem.test1
    }

    override fun areContentsTheSame(oldItem: FeedComment, newItem: FeedComment): Boolean {
        return oldItem == newItem
    }
}