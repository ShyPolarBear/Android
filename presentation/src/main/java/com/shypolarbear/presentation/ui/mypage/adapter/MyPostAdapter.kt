package com.shypolarbear.presentation.ui.mypage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.mypage.MyFeed
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.databinding.ItemPagePostBinding
import com.shypolarbear.presentation.ui.mypage.diffutil.MyPostDiffUtilCallback
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.MyFeedType
import java.util.Collections.addAll

class MyPostAdapter(private val _items: List<MyFeed?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var postBinding: ItemPagePostBinding

    inner class ItemPostViewHolder(private val binding: ItemPagePostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: MyFeed) {
            binding.apply {
                tvItemPageTitle.text = item.title
                GlideUtil.loadImage(binding.root.context, item.feedImage, ivItemPage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_items[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            postBinding =
                ItemPagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemPostViewHolder(postBinding)
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

    fun updateList(items: List<MyFeed?>) {
        items.let {
            val diffCallback = MyPostDiffUtilCallback(_items, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            _items.toMutableList().run {
                clear()
                addAll(items)
                diffResult.dispatchUpdatesTo(this@MyPostAdapter)
            }
        }
    }
}