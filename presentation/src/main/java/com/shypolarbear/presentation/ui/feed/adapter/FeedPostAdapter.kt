package com.shypolarbear.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.feed.FeedViewModel

class FeedPostAdapter: ListAdapter<FeedPost, FeedPostAdapter.FeedPostViewHolder>(FeedPostDiffCallback()) {

    private lateinit var binding : ItemFeedBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostViewHolder {
        binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FeedPostViewHolder(private val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: FeedPost) {
            binding.feedPost = post
            binding.executePendingBindings()

            with(binding.viewpagerFeedPostImg) {
                adapter = FeedPostImgAdapter().apply {
                    submitList(mutableListOf(
                        FeedPostImg("1"),
                        FeedPostImg("1"),
                        FeedPostImg("1"),
                        FeedPostImg("1")
                    ))
                }

                TabLayoutMediator(binding.tablayoutFeedPostIndicator, this
                ) { tab, position ->

                }.attach()
            }
        }
    }
}

class FeedPostDiffCallback : DiffUtil.ItemCallback<FeedPost>() {

    override fun areItemsTheSame(oldItem: FeedPost, newItem: FeedPost): Boolean {
        return oldItem.testContent == newItem.testContent
    }

    override fun areContentsTheSame(oldItem: FeedPost, newItem: FeedPost): Boolean {
        return oldItem == newItem
    }
}