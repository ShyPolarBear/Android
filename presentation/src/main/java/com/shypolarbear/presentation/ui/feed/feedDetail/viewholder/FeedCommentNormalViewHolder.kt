package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding

class FeedCommentNormalViewHolder (
    private val binding: ItemFeedCommentNormalBinding,
    private val onMyCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button) -> Unit = { _ -> }
    ) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnFeedCommentNormalLike.setOnClickListener {
            onBtnLikeClick(binding.btnFeedCommentNormalLike)
        }
    }

    fun bind(comment: FeedComment) {
        // Todo(일반 댓글)

        when(comment.owner) {
            "my" -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    onMyCommentPropertyClick(binding.ivFeedCommentNormalProperty)
                }
            }

            "other" -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    onOtherCommentPropertyClick(binding.ivFeedCommentNormalProperty)
                }
            }
        }

        binding.executePendingBindings()
    }
}