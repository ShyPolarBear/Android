package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding

class FeedReplyNormalViewHolder (
    private val binding: ItemFeedReplyNormalBinding,
    private val onMyReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button) -> Unit = { _ -> }
    ) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnFeedReplyNormalLike.setOnClickListener {
            onBtnLikeClick(binding.btnFeedReplyNormalLike)
        }
    }

    fun bind(comment: FeedComment) {
        // Todo(일반 대댓글)

        when(comment.owner) {
            "my" -> {
                binding.ivFeedReplyNormalProperty.setOnClickListener {
                    onMyReplyPropertyClick(binding.ivFeedReplyNormalProperty)
                }
            }

            "other" -> {
                binding.ivFeedReplyNormalProperty.setOnClickListener {
                    onOtherReplyPropertyClick(binding.ivFeedReplyNormalProperty)
                }
            }
        }

        binding.executePendingBindings()
    }
}