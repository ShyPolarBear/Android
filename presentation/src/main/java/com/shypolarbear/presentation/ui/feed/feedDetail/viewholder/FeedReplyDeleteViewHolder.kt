package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.shypolarbear.presentation.databinding.ItemFeedReplyDeleteBinding

class FeedReplyDeleteViewHolder(private val binding: ItemFeedReplyDeleteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: ChildComment) {
        // Todo(삭제된 대댓글)
        binding.executePendingBindings()
    }
}
