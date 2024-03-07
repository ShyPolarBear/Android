package com.beeeam.ui

import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ui.databinding.ItemFeedPostImgBinding
import com.bumptech.glide.Glide

class ImageViewPagerViewHolder(private val binding: ItemFeedPostImgBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(img: String) {
        Glide.with(itemView)
            .load(img)
            .into(binding.ivFeedPost)
        binding.executePendingBindings()
    }
}
