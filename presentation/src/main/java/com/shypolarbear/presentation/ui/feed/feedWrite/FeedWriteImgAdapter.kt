package com.shypolarbear.presentation.ui.feed.feedWrite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.databinding.ItemFeedWriteImgBinding

class FeedWriteImgAdapter(): ListAdapter<FeedWriteImg, FeedWriteImgViewHolder>(FeedWriteImgDiffCallback()) {

    private lateinit var binding : ItemFeedWriteImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedWriteImgViewHolder {
        binding = ItemFeedWriteImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedWriteImgViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedWriteImgViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FeedWriteImgDiffCallback : DiffUtil.ItemCallback<FeedWriteImg>() {

    override fun areItemsTheSame(oldItem: FeedWriteImg, newItem: FeedWriteImg): Boolean {
        return oldItem.imgUrl == newItem.imgUrl
    }

    override fun areContentsTheSame(oldItem: FeedWriteImg, newItem: FeedWriteImg): Boolean {
        return oldItem == newItem
    }
}