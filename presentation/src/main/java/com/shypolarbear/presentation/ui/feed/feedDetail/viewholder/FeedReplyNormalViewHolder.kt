package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding

class FeedReplyNormalViewHolder (private val binding: ItemFeedReplyNormalBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: FeedComment) {
        // Todo(일반 대댓글)
        binding.executePendingBindings()
    }
}