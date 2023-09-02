package com.shypolarbear.presentation.ui.feed.feedTotal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedCommentViewType
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentDeleteViewHolder
import com.shypolarbear.presentation.ui.feed.feedDetail.viewholder.FeedCommentNormalViewHolder
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalLikeBtnType
import com.shypolarbear.presentation.ui.feed.feedTotal.viewholder.FeedLoadingViewHolder
import com.shypolarbear.presentation.ui.feed.feedTotal.viewholder.FeedPostViewHolder
import timber.log.Timber

enum class FeedViewType(val viewType: Int) {
    LOADING(0),
    ITEM(1)
}

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
    ): ListAdapter<Feed, RecyclerView.ViewHolder>(FeedPostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            FeedViewType.LOADING.viewType -> {
                return FeedLoadingViewHolder(
                    ItemFeedLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            FeedViewType.ITEM.viewType -> {
                return FeedPostViewHolder(
                    ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onMyPostPropertyClick = onMyPostPropertyClick,
                    onOtherPostPropertyClick = onOtherPostPropertyClick,
                    onMyBestCommentPropertyClick = onMyBestCommentPropertyClick,
                    onOtherBestCommentPropertyClick = onOtherBestCommentPropertyClick,
                    onBtnLikeClick = onBtnLikeClick,
                    onMoveToDetailClick = onMoveToDetailClick
                )
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position).feedId) {
            0 -> {
                (holder as FeedLoadingViewHolder).bind(getItem(position))
            }
            else -> {
                (holder as FeedPostViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Timber.d("feedId: ${getItem(position).feedId}")
        return when(getItem(position).feedId) {
            0 -> {
                FeedViewType.LOADING.viewType
            }
            else -> {
                FeedViewType.ITEM.viewType
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