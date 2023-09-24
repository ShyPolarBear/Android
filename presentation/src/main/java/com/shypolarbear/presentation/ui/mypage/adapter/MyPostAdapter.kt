package com.shypolarbear.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.databinding.ItemPagePostBinding
import com.shypolarbear.presentation.util.MyFeedType

class MyPostAdapter (private val items: List<String?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var postBinding: ItemPagePostBinding

    companion object{
        fun initPostAdapter(items: List<String>) =
            MyPostAdapter(items)
    }

    inner class ItemPostViewHolder(private val binding: ItemPagePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: String) {
            binding.apply {
                tvItemPageTitle.text = item
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            postBinding = ItemPagePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemPostViewHolder(postBinding)
        } else {
            LoadingViewHolder(ItemFeedLoadingBinding.inflate( LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemPostViewHolder) {
            holder.bindItems(items[position]!!)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}