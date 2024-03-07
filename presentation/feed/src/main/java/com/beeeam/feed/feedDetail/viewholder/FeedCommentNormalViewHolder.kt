package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedCommentNormalBinding
import com.beeeam.util.FeedDetailLikeBtnType
import com.beeeam.util.GlideUtil
import com.beeeam.util.showLikeBtnIsLike
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedReplyAdapter

class FeedCommentNormalViewHolder(
    private val binding: ItemFeedCommentNormalBinding,
    private val onMyCommentPropertyClick: (view: ImageView, commentId: Int, position: Int, commentAuthor: String, content: String) -> Unit = { _, _, _, _, _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView, commentId: Int, position: Int, commentAuthor: String) -> Unit = { _, _, _, _ -> },
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
    private val onItemClick: () -> Unit,
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
                FeedDetailLikeBtnType.COMMENT_LIKE_BTN,
            )
        }

        binding.ivFeedCommentNormalProperty.setOnClickListener {
            when (comment.isAuthor) {
                true ->
                    onMyCommentPropertyClick(binding.ivFeedCommentNormalProperty, comment.commentId, adapterPosition, comment.authorNickname, comment.content)
                false ->
                    onOtherCommentPropertyClick(binding.ivFeedCommentNormalProperty, comment.commentId, adapterPosition, comment.authorNickname)
            }
        }

        binding.layoutFeedCommentNormalTotal.setOnClickListener {
            onItemClick()
        }
    }

    fun bind(item: Comment) {
        // Todo(일반 댓글)
        comment = item
        setComment(item)

        binding.executePendingBindings()
    }

    private fun setComment(item: Comment) {
        val feedReplyAdapter = FeedReplyAdapter(
            onMyReplyPropertyClick = onMyReplyPropertyClick,
            onOtherReplyPropertyClick = onOtherReplyPropertyClick,
            onBtnLikeClick = onBtnLikeClick,
            parentCommentId = item.commentId,
        )

        binding.rvFeedCommentReply.adapter = feedReplyAdapter
        feedReplyAdapter.submitList(item.childComments)

        binding.tvFeedCommentNormalNickname.text = item.authorNickname
        binding.tvFeedCommentNormalContent.text = item.content
        binding.tvFeedCommentNormalTime.text = item.createdDate

        binding.btnFeedCommentNormalLike.showLikeBtnIsLike(item.isLike, binding.btnFeedCommentNormalLike)
        binding.tvFeedCommentNormalLikeCnt.text = item.likeCount.toString()

        if (!item.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, item.authorProfileImage, binding.ivFeedCommentNormalProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedCommentNormalProfile, placeHolder = com.beeeam.designsystem.R.drawable.ic_user_base_profile)
        }
    }
}
