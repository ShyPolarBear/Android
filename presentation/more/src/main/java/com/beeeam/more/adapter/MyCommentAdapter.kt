package com.beeeam.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.myinfo.databinding.ItemPageCommentBinding
import com.beeeam.ui.LoadingViewHolder
import com.beeeam.ui.databinding.ItemFeedLoadingBinding
import com.beeeam.util.GlideUtil
import com.beeeam.util.MyFeedType
import com.shypolarbear.domain.model.mypage.MyCommentFeed

class MyCommentAdapter(private val _items: List<MyCommentFeed?>) :
    ListAdapter<MyCommentFeed, RecyclerView.ViewHolder>(MyCommentDiffCallback()) {

    inner class ItemCommentViewHolder(private val binding: ItemPageCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: MyCommentFeed) {
            binding.apply {
                tvItemPage.text = item.title
                item.feedImage?.let { image ->
                    GlideUtil.loadImage(binding.root.context, image, ivItemPage)
                }
                tvItemAuthor.text = item.author
                item.authorProfileImage?.let { image ->
                    GlideUtil.loadImage(binding.root.context, image, ivItemProfile)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_items[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            ItemCommentViewHolder(ItemPageCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        if (holder is ItemCommentViewHolder) {
            holder.bindItems(_items[position]!!)
        }
    }

    override fun getItemCount(): Int {
        return _items.size
    }
}

class MyCommentDiffCallback : DiffUtil.ItemCallback<MyCommentFeed>() {

    override fun areItemsTheSame(oldItem: MyCommentFeed, newItem: MyCommentFeed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: MyCommentFeed, newItem: MyCommentFeed): Boolean {
        return oldItem == newItem
    }
}
