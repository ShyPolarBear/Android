package com.beeeam.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedReplyDeleteBinding
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment

class FeedReplyDeleteViewHolder(private val binding: ItemFeedReplyDeleteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: ChildComment) {
        // Todo(삭제된 대댓글)
        binding.executePendingBindings()
    }
}
