package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.presentation.databinding.ItemFeedCommentLoadingBinding

class FeedCommentLoadingViewHolder(private val binding: ItemFeedCommentLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Comment) {
        binding.executePendingBindings()
    }
}
