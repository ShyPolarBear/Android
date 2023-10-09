package com.shypolarbear.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.mypage.MyFeed
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.databinding.ItemPagePostBinding
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.MyFeedType

class MyPostAdapter(
    private val _items: List<MyFeed?>,
    private val onMyFeedPropertyClick: (feedId: Int, view: ImageView) -> Unit = { _, _ -> },
) :
    ListAdapter<MyFeed, RecyclerView.ViewHolder>(MyFeedDiffCallback()) {

    inner class ItemPostViewHolder(
        private val binding: ItemPagePostBinding,
        private val onMyFeedPropertyClick: (feedId: Int, view: ImageView) -> Unit = { _, _ -> },
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var myFeed: MyFeed
        init {
            binding.ivItemPostProperty.setOnClickListener {
                onMyFeedPropertyClick(myFeed.feedId, binding.ivItemPostProperty)
            }
        }
        fun bindItems(item: MyFeed) {
            myFeed = item
            binding.apply {
                tvItemPageTitle.text = item.title
                GlideUtil.loadImage(binding.root.context, item.feedImage, ivItemPage)
                ivItemPostProperty.setOnClickListener {
                    onMyFeedPropertyClick(item.feedId, ivItemPostProperty)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_items[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            ItemPostViewHolder(
                ItemPagePostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMyFeedPropertyClick = onMyFeedPropertyClick
            )
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
        if (holder is ItemPostViewHolder) {
            holder.bindItems(_items[position]!!)
        }
    }

    override fun getItemCount(): Int {
        return _items.size
    }
}

class MyFeedDiffCallback : DiffUtil.ItemCallback<MyFeed>() {

    override fun areItemsTheSame(oldItem: MyFeed, newItem: MyFeed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: MyFeed, newItem: MyFeed): Boolean {
        return oldItem == newItem
    }
}