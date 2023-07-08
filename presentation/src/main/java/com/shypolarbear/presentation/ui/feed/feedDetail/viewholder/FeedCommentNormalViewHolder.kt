package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.databinding.ItemFeedPostImgBinding

class FeedCommentNormalViewHolder (private val binding: ItemFeedCommentNormalBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: FeedComment) {
        // Todo(일반 댓글)
        binding.executePendingBindings()
    }
}