package com.beeeam.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.beeeam.ui.databinding.ItemFeedPostImgBinding

class ImageViewPagerAdapter : ListAdapter<String, ImageViewPagerViewHolder>(FeedPostImgDiffCallback()) {

    private lateinit var binding: ItemFeedPostImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewPagerViewHolder {
        binding = ItemFeedPostImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FeedPostImgDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
