package com.shypolarbear.presentation.ui.noti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.noti.NotificationContent
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.databinding.ItemNotiBinding
import com.shypolarbear.presentation.ui.mypage.adapter.LoadingViewHolder
import com.shypolarbear.presentation.util.MyFeedType
import com.shypolarbear.presentation.util.NotificationType

class NotiAdapter : ListAdapter<NotificationContent, RecyclerView.ViewHolder>(NotiDiffCallback()) {
    inner class ItemNotiViewHolder(private val binding: ItemNotiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(totalNoti: NotificationContent) {
            binding.apply {
                when (totalNoti.notificationType) {
                    NotificationType.NEW_FEED_COMMENT.type -> {

                    }

                    NotificationType.NEW_COMMENT_CHILD_COMMENT.type -> {

                    }
                }
                itemTvDate.text = totalNoti.createdDate
                itemTvTitle.text = totalNoti.title
                itemTvDetail.text =
                    binding.root.context.getString(R.string.noti_detail, totalNoti.content)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            ItemNotiViewHolder(
                ItemNotiBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
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
        if (holder is ItemNotiViewHolder) {
            holder.bindItems(currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class NotiDiffCallback : DiffUtil.ItemCallback<NotificationContent>() {
    override fun areItemsTheSame(
        oldItem: NotificationContent,
        newItem: NotificationContent,
    ): Boolean {
        return oldItem.notificationId == newItem.notificationId
    }

    override fun areContentsTheSame(
        oldItem: NotificationContent,
        newItem: NotificationContent,
    ): Boolean {
        return oldItem == newItem
    }
}