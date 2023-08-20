package com.shypolarbear.presentation.ui.feed.feedWrite

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.databinding.ItemFeedWriteImgBinding
import com.shypolarbear.presentation.util.GlideUtil

class FeedWriteImgViewHolder(
    private val binding: ItemFeedWriteImgBinding
): RecyclerView.ViewHolder(binding.root) {
    init {

    }

    fun bind(item: FeedWriteImg) {
        binding.apply {
            GlideUtil.loadImage(itemView.context, item.imgUrl, binding.ivFeedWriteImg)

            executePendingBindings()
        }
    }
}