package com.shypolarbear.presentation.ui.quiz.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.databinding.ItemQuizMainRecentFeedBinding
import com.shypolarbear.presentation.ui.mypage.adapter.LoadingViewHolder
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.MyFeedType

class QuizMainAdapter:
    ListAdapter<Feed, RecyclerView.ViewHolder>(RecentFeedDiffCallback())  {
    inner class ItemRecentFeedViewHolder(private val binding: ItemQuizMainRecentFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: Feed) {
            binding.apply {
                itemQuizMainTv.text = item.title
                GlideUtil.loadImage(binding.root.context, item.feedImages.first(), itemQuizMainIv)
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
                    false
                )
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