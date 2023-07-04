package com.shypolarbear.presentation.ui.feed.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.databinding.ItemFeedPostImgBinding

class FeedPostImgViewHolder(private val binding: ItemFeedPostImgBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(img: FeedPostImg) {
        Glide.with(itemView)
            .load(img.postImgUrl)
            .into(binding.ivFeedPost)
        binding.executePendingBindings()
    }
}