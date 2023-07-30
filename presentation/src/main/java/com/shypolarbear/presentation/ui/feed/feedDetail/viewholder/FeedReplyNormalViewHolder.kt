package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding
import com.shypolarbear.presentation.util.showLikeBtnIsLike

class FeedReplyNormalViewHolder (
    private val binding: ItemFeedReplyNormalBinding,
    private val onMyReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button, isLiked: Boolean, likeCnt: Int, textView: TextView) -> Int = { _, _, _, _ -> 0}
    ) : RecyclerView.ViewHolder(binding.root) {

    private var childComment: ChildComment = ChildComment()

    init {
        var btnClicked = false
        var isReplyLike = false
        var replyLikeCnt = 0

        binding.btnFeedReplyNormalLike.setOnClickListener {
            when(btnClicked) {
                true -> {
                    isReplyLike = isReplyLike
                    replyLikeCnt = replyLikeCnt
                }
                false -> {
                    isReplyLike = childComment.isLike
                    replyLikeCnt = childComment.likeCount
                }
            }

            replyLikeCnt = onBtnLikeClick(
                binding.btnFeedReplyNormalLike,
                isReplyLike,
                replyLikeCnt,
                binding.tvFeedReplyNormalLikeCnt
            )
            isReplyLike = !isReplyLike
            btnClicked = true
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

    private fun setReply(reply: ChildComment) {
        binding.tvFeedReplyNormalNickname.text = reply.author
        binding.tvFeedReplyNormalContent.text = reply.content
        binding.tvFeedReplyNormalTime.text = reply.createdDate

        binding.btnFeedReplyNormalLike.showLikeBtnIsLike(reply.isLike, binding.btnFeedReplyNormalLike)
        binding.tvFeedReplyNormalLikeCnt.text = reply.likeCount.toString()

        if (!reply.authorProfileImage.isNullOrBlank()) {
            Glide.with(itemView)
                .load(reply.authorProfileImage)
                .into(binding.ivFeedReplyNormalProfile)
        }
    }
}