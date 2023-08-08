package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailLikeBtnType
import com.shypolarbear.presentation.util.showLikeBtnIsLike

class FeedReplyNormalViewHolder (
    private val binding: ItemFeedReplyNormalBinding,
    private val onMyReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        commentId: Int,
        replyId: Int,
        itemType: FeedDetailLikeBtnType
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val parentCommentId: Int
    ) : RecyclerView.ViewHolder(binding.root) {

    private var childComment: ChildComment = ChildComment()

    init {
        binding.btnFeedReplyNormalLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedReplyNormalLike,
                childComment.isLike,
                childComment.likeCount,
                binding.tvFeedReplyNormalLikeCnt,
                parentCommentId,
                childComment.commentId,
                FeedDetailLikeBtnType.REPLY_LIKE_BTN
            )
        }

        binding.ivFeedReplyNormalProperty.setOnClickListener {
            when(childComment.isAuthor) {
                true ->
                    onMyReplyPropertyClick(binding.ivFeedReplyNormalProperty)
                false ->
                    onOtherReplyPropertyClick(binding.ivFeedReplyNormalProperty)
            }
        }

    }

    fun bind(item: ChildComment) {
        // Todo(일반 대댓글)

        childComment = item
        setReply(item)

        binding.executePendingBindings()
    }

    private fun setReply(item: ChildComment) {
        binding.tvFeedReplyNormalNickname.text = item.author
        binding.tvFeedReplyNormalContent.text = item.content
        binding.tvFeedReplyNormalTime.text = item.createdDate

        binding.btnFeedReplyNormalLike.showLikeBtnIsLike(item.isLike, binding.btnFeedReplyNormalLike)
        binding.tvFeedReplyNormalLikeCnt.text = item.likeCount.toString()

        if (!item.authorProfileImage.isNullOrBlank()) {
            Glide.with(itemView)
                .load(item.authorProfileImage)
                .into(binding.ivFeedReplyNormalProfile)
        } else {
            Glide.with(itemView)
                .load(R.drawable.ic_user_base_profile)
                .into(binding.ivFeedReplyNormalProfile)
        }
    }
}