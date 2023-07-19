package com.shypolarbear.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedTotal
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.feed.viewholder.FeedPostViewHolder

class FeedPostAdapter(
    private val onMyPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button, isLiked: Boolean, likeCnt: Int, textView: TextView) -> Int = { _, _, _, _ -> 0},
    private val onMoveToDetailClick: () -> Unit = { }
    ): ListAdapter<Feed, FeedPostViewHolder>(FeedPostDiffCallback()) {

    private lateinit var binding : ItemFeedBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostViewHolder {
        binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedPostViewHolder(
            binding,
            onMyPostPropertyClick = onMyPostPropertyClick,
            onOtherPostPropertyClick = onOtherPostPropertyClick,
            onMyBestCommentPropertyClick = onMyBestCommentPropertyClick,
            onOtherBestCommentPropertyClick = onOtherBestCommentPropertyClick,
            onBtnLikeClick = onBtnLikeClick,
            onMoveToDetailClick = onMoveToDetailClick
        )
    }

    override fun onBindViewHolder(holder: FeedPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FeedPostDiffCallback : DiffUtil.ItemCallback<Feed>() {

    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.feedId == newItem.feedId
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }
}