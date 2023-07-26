package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.presentation.databinding.ItemFeedCommentDeleteBinding

class FeedCommentDeleteViewHolder (
    private val binding: ItemFeedCommentDeleteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment) {
        // Todo(삭제된 댓글)
        binding.executePendingBindings()
    }
}