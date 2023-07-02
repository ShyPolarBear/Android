package com.shypolarbear.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.databinding.ItemFeedPostImgBinding

class FeedPostImgAdapter : ListAdapter<FeedPostImg, FeedPostImgAdapter.FeedPostViewHolder>(FeedPostImgDiffCallback()) {

    private lateinit var binding : ItemFeedPostImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostViewHolder {
        binding = ItemFeedPostImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FeedPostViewHolder(private val binding: ItemFeedPostImgBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(img: FeedPostImg) {
            binding.feedPostImg = img
            binding.executePendingBindings()
        }
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