package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailLikeBtnType
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedReplyAdapter
import com.shypolarbear.presentation.util.showLikeBtnIsLike

class FeedCommentNormalViewHolder (
    private val binding: ItemFeedCommentNormalBinding,
    private val onMyCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
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
    ) -> Unit = { _, _, _, _, _, _, _ -> }
    ) : RecyclerView.ViewHolder(binding.root) {

    private var comment: Comment = Comment()

    init {
        binding.btnFeedCommentNormalLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedCommentNormalLike,
                comment.isLike,
                comment.likeCount,
                binding.tvFeedCommentNormalLikeCnt,
                comment.commentId,
                0,
                FeedDetailLikeBtnType.COMMENT_LIKE_BTN
            )
        }

        binding.ivFeedCommentNormalProperty.setOnClickListener {
            when(comment.isAuthor) {
                true ->
                    onMyCommentPropertyClick(binding.ivFeedCommentNormalProperty)
                false ->
                    onOtherCommentPropertyClick(binding.ivFeedCommentNormalProperty)
            }
        }
    }

    fun bind(item: Comment) {
        // Todo(일반 댓글)
        comment = item
        setComment(item)

        binding.executePendingBindings()
    }

    private fun setComment(comment: Comment) {
        val feedReplyAdapter = FeedReplyAdapter(
            onMyReplyPropertyClick = onMyReplyPropertyClick,
            onOtherReplyPropertyClick = onOtherReplyPropertyClick,
            onBtnLikeClick = onBtnLikeClick,
            parentCommentId = comment.commentId
        )

        binding.rvFeedCommentReply.adapter = feedReplyAdapter
        feedReplyAdapter.submitList(comment.childComments)

        binding.tvFeedCommentNormalNickname.text = comment.author
        binding.tvFeedCommentNormalContent.text = comment.content
        binding.tvFeedCommentNormalTime.text = comment.createdDate

        binding.btnFeedCommentNormalLike.showLikeBtnIsLike(comment.isLike, binding.btnFeedCommentNormalLike)
        binding.tvFeedCommentNormalLikeCnt.text = comment.likeCount.toString()

        if (!comment.authorProfileImage.isNullOrBlank()) {
            Glide.with(itemView)
                .load(comment.authorProfileImage)
                .into(binding.ivFeedCommentNormalProfile)
        }
    }
}