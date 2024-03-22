package com.beeeam.feed.feedTotal.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedLoadingBinding
import com.shypolarbear.domain.model.feed.Feed

class FeedLoadingViewHolder(private val binding: ItemFeedLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Feed) {
        binding.executePendingBindings()
    }
}
