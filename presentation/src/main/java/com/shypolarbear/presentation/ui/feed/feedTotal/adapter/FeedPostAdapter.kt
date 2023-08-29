package com.shypolarbear.presentation.ui.feed.feedTotal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalLikeBtnType
import com.shypolarbear.presentation.ui.feed.feedTotal.viewholder.FeedPostViewHolder

class FeedPostAdapter(
    private val onMyPostPropertyClick: (view: ImageView, feedId: Int, position: Int) -> Unit = { _, _, _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        feedId: Int,
        itemType: FeedTotalLikeBtnType
            ) -> Unit = { _, _, _, _, _, _ -> },
    private val onMoveToDetailClick: (feedId: Int) -> Unit = { }
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