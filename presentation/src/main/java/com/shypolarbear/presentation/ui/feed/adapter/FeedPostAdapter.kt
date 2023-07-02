package com.shypolarbear.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.feed.viewholder.FeedPostViewHolder

class FeedPostAdapter(private val viewLifeCycleOwner: LifecycleOwner): ListAdapter<FeedPost, FeedPostViewHolder>(FeedPostDiffCallback()) {

    private lateinit var binding : ItemFeedBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostViewHolder {
        binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPostViewHolder(binding, viewLifeCycleOwner)
    }

    override fun onBindViewHolder(holder: FeedPostViewHolder, position: Int) {
        holder.bind(getItem(position))
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