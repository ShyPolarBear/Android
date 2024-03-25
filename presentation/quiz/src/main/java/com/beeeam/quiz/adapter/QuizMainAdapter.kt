package com.beeeam.quiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.quiz.databinding.ItemQuizMainRecentFeedBinding
import com.beeeam.ui.LoadingViewHolder
import com.beeeam.ui.databinding.ItemFeedLoadingBinding
import com.beeeam.util.GlideUtil
import com.beeeam.util.MyFeedType
import com.shypolarbear.domain.model.feed.Feed

class QuizMainAdapter(private val onMoveToDetailClick: (feedId: Int) -> Unit = { _ -> }) :
    ListAdapter<Feed, RecyclerView.ViewHolder>(RecentFeedDiffCallback()) {
    inner class ItemRecentFeedViewHolder(private val binding: ItemQuizMainRecentFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: Feed) {
            binding.apply {
                itemQuizMainTv.text = item.title
                itemRecentFeed.setOnClickListener { onMoveToDetailClick(item.feedId) }
                if (item.feedImages.isNullOrEmpty()) {
                    GlideUtil.loadImage(binding.root.context, "", itemQuizMainIv)
                } else {
                    GlideUtil.loadImage(binding.root.context, item.feedImages.first(), itemQuizMainIv)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            ItemRecentFeedViewHolder(ItemQuizMainRecentFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            LoadingViewHolder(
                ItemFeedLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemRecentFeedViewHolder) {
            holder.bindItems(currentList[position]!!)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class RecentFeedDiffCallback : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }
}
