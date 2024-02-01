package com.shypolarbear.presentation.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.presentation.databinding.ItemFeedPostImgBinding

class ImageViewPagerViewHolder(private val binding: ItemFeedPostImgBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(img: String) {
        Glide.with(itemView)
            .load(img)
            .into(binding.ivFeedPost)
        binding.executePendingBindings()
    }
}
