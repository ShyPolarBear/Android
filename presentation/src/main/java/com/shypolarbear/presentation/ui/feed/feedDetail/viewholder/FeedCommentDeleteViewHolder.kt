package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedCommentDeleteBinding
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.util.FunctionUtil
import com.skydoves.powermenu.PowerMenuItem

class FeedCommentDeleteViewHolder (
    private val binding: ItemFeedCommentDeleteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: FeedComment) {
        // Todo(삭제된 댓글)
        binding.executePendingBindings()
    }
}