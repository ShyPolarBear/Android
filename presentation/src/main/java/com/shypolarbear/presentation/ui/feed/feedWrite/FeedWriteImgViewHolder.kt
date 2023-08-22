package com.shypolarbear.presentation.ui.feed.feedWrite

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.databinding.ItemFeedWriteImgBinding
import com.shypolarbear.presentation.util.GlideUtil
import timber.log.Timber

class FeedWriteImgViewHolder(
    private val binding: ItemFeedWriteImgBinding,
    private val onRemoveImgClick: (position: Int) -> Unit = { _ -> }
): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.apply {
            btnRemoveUploadImg.setOnClickListener {
                onRemoveImgClick(adapterPosition)
                Timber.d("$adapterPosition 번째 아이템")
            }
        }
    }

    fun bind(item: FeedWriteImg) {
        binding.apply {
            GlideUtil.loadImage(itemView.context, item.imgUrl.toUri(), binding.ivFeedWriteImg)
            Timber.d("이미지 uri: $item.imgUrl")
            executePendingBindings()
        }
    }
}