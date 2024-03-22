package com.beeeam.feed.feedWrite

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedWriteImgBinding
import com.beeeam.util.GlideUtil

class FeedWriteImgViewHolder(
    private val binding: ItemFeedWriteImgBinding,
    private val onRemoveImgClick: (position: Int) -> Unit = { _ -> },
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.apply {
            btnRemoveUploadImg.setOnClickListener {
                onRemoveImgClick(adapterPosition)
            }
        }
    }

    fun bind(item: String) {
        binding.apply {
            GlideUtil.loadImage(itemView.context, item.toUri(), binding.ivFeedWriteImg)
            executePendingBindings()
        }
    }
}
