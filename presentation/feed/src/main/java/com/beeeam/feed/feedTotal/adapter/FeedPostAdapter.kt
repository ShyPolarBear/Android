package com.shypolarbear.presentation.ui.feed.feedTotal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedBinding
import com.beeeam.feed.databinding.ItemFeedNoImageBinding
import com.beeeam.ui.databinding.ItemFeedLoadingBinding
import com.beeeam.util.FeedViewType
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalLikeBtnType
import com.shypolarbear.presentation.ui.feed.feedTotal.viewholder.FeedLoadingViewHolder
import com.shypolarbear.presentation.ui.feed.feedTotal.viewholder.FeedPostNoImageViewHolder
import com.shypolarbear.presentation.ui.feed.feedTotal.viewholder.FeedPostViewHolder

class FeedPostAdapter(
    private val onMyPostPropertyClick: (view: ImageView, feedId: Int, position: Int) -> Unit = { _, _, _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView, commentId: Int, content: String, commentView: View) -> Unit = { _, _, _, _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        feedId: Int,
        commentId: Int?,
        itemType: FeedTotalLikeBtnType,
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val onMoveToDetailClick: (feedId: Int) -> Unit = { _ -> },
) : ListAdapter<Feed, RecyclerView.ViewHolder>(FeedPostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FeedViewType.LOADING.viewType -> {
                return FeedLoadingViewHolder(
                    ItemFeedLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                )
            }
            FeedViewType.ITEM_HAS_IMAGES.viewType -> {
                return FeedPostViewHolder(
                    ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onMyPostPropertyClick = onMyPostPropertyClick,
                    onOtherPostPropertyClick = onOtherPostPropertyClick,
                    onMyBestCommentPropertyClick = onMyBestCommentPropertyClick,
                    onOtherBestCommentPropertyClick = onOtherBestCommentPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onMoveToDetailClick = onMoveToDetailClick,
                )
            }
            FeedViewType.ITEM_HAS_NO_IMAGES.viewType -> {
                return FeedPostNoImageViewHolder(
                    ItemFeedNoImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onMyPostPropertyClick = onMyPostPropertyClick,
                    onOtherPostPropertyClick = onOtherPostPropertyClick,
                    onMyBestCommentPropertyClick = onMyBestCommentPropertyClick,
                    onOtherBestCommentPropertyClick = onOtherBestCommentPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onMoveToDetailClick = onMoveToDetailClick,
                )
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItem(position).feedId == 0 -> {
                (holder as FeedLoadingViewHolder).bind(getItem(position))
            }
            getItem(position).feedImages.isNullOrEmpty() -> {
                (holder as FeedPostNoImageViewHolder).bind(getItem(position))
            }
            else -> {
                (holder as FeedPostViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            getItem(position).feedId == 0 -> {
                FeedViewType.LOADING.viewType
            }
            getItem(position).feedImages.isNullOrEmpty() -> {
                FeedViewType.ITEM_HAS_NO_IMAGES.viewType
            }
            else -> {
                FeedViewType.ITEM_HAS_IMAGES.viewType
            }
        }
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
