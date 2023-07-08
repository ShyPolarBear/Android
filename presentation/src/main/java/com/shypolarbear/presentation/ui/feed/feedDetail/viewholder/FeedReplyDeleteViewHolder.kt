package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.databinding.ItemFeedReplyDeleteBinding

class FeedReplyDeleteViewHolder (private val binding: ItemFeedReplyDeleteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: FeedComment) {
        // Todo(삭제된 대댓글)
        binding.executePendingBindings()
    }
}