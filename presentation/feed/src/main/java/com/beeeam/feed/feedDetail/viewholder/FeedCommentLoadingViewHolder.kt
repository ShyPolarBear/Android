package com.beeeam.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedCommentLoadingBinding
import com.shypolarbear.domain.model.feed.Comment

class FeedCommentLoadingViewHolder(private val binding: ItemFeedCommentLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Comment) {
        binding.executePendingBindings()
    }
}
