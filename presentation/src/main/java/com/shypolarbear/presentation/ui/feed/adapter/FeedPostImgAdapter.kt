package com.shypolarbear.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.databinding.ItemFeedPostImgBinding
import com.shypolarbear.presentation.ui.feed.viewholder.FeedPostImgViewHolder

class FeedPostImgAdapter : ListAdapter<FeedPostImg, FeedPostImgViewHolder>(FeedPostImgDiffCallback()) {

    private lateinit var binding : ItemFeedPostImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostImgViewHolder {
        binding = ItemFeedPostImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPostImgViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedPostImgViewHolder, position: Int) {
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