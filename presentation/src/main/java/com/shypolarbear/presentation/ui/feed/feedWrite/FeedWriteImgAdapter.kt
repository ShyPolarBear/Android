package com.shypolarbear.presentation.ui.feed.feedWrite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.presentation.databinding.ItemFeedWriteImgBinding

class FeedWriteImgAdapter(
    private val onRemoveImgClick: (position: Int) -> Unit = { _ -> }
): ListAdapter<String, FeedWriteImgViewHolder>(FeedWriteImgDiffCallback()) {

    private lateinit var binding : ItemFeedWriteImgBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedWriteImgViewHolder {
        binding = ItemFeedWriteImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedWriteImgViewHolder(
            binding,
            onRemoveImgClick = onRemoveImgClick,
        )
    }

    override fun onBindViewHolder(holder: FeedWriteImgViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FeedWriteImgDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}