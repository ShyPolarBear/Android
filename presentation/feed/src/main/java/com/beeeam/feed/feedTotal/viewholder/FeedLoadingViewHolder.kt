package com.shypolarbear.presentation.ui.feed.feedTotal.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding

class FeedLoadingViewHolder(private val binding: ItemFeedLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Feed) {
        binding.executePendingBindings()
    }
}
