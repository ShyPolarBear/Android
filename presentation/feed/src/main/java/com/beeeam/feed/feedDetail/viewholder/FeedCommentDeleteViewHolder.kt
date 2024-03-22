package com.beeeam.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedCommentDeleteBinding
import com.beeeam.util.FeedDetailLikeBtnType
import com.shypolarbear.domain.model.feed.Comment
import com.beeeam.feed.feedDetail.adapter.FeedReplyAdapter

class FeedCommentDeleteViewHolder(
    private val binding: ItemFeedCommentDeleteBinding,
    private val onMyReplyPropertyClick: (view: ImageView, commentId: Int, feedId: Int, content: String) -> Unit = { _, _, _, _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        commentId: Int,
        replyId: Int,
        itemType: FeedDetailLikeBtnType,
    ) -> Unit = { _, _, _, _, _, _, _ -> },
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Comment) {
        setReply(item)
        binding.executePendingBindings()
    }

    private fun setReply(item: Comment) {
        val feedReplyAdapter = FeedReplyAdapter(
            onMyReplyPropertyClick = onMyReplyPropertyClick,
            onOtherReplyPropertyClick = onOtherReplyPropertyClick,
            onBtnLikeClick = onBtnLikeClick,
            parentCommentId = item.commentId,
        )

        binding.rvFeedDeleteCommentReply.adapter = feedReplyAdapter
        feedReplyAdapter.submitList(item.childComments)
    }
}
