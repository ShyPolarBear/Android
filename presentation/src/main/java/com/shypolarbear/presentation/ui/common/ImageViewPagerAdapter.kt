package com.shypolarbear.presentation.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.databinding.ItemFeedPostImgBinding

class ImageViewPagerAdapter : ListAdapter<FeedPostImg, ImageViewPagerViewHolder>(FeedPostImgDiffCallback()) {

    private lateinit var binding : ItemFeedPostImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewPagerViewHolder {
        binding = ItemFeedPostImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FeedPostImgDiffCallback : DiffUtil.ItemCallback<FeedPostImg>() {

    override fun areItemsTheSame(oldItem: FeedPostImg, newItem: FeedPostImg): Boolean {
        return oldItem.postImgUrl == newItem.postImgUrl
    }

    override fun areContentsTheSame(oldItem: FeedPostImg, newItem: FeedPostImg): Boolean {
        return oldItem == newItem
    }
}