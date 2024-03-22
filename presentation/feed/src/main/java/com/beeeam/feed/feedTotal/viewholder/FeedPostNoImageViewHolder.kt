package com.beeeam.feed.feedTotal.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedNoImageBinding
import com.beeeam.util.FeedTotalLikeBtnType
import com.beeeam.util.GlideUtil
import com.beeeam.util.showLikeBtnIsLike
import com.shypolarbear.domain.model.feed.Feed

class FeedPostNoImageViewHolder(
    private val binding: ItemFeedNoImageBinding,
    private val onMyPostPropertyClick: (view: ImageView, feedId: Int, position: Int) -> Unit = { _, _, _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView, commentId: Int, content: String, commentView: View) -> Unit = { _, _, _, _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        feedId: Int,
        commentId: Int?,
        itemType: FeedTotalLikeBtnType,
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val onMoveToDetailClick: (feedId: Int) -> Unit = { _ -> },
) : RecyclerView.ViewHolder(binding.root) {

    private var post: Feed = Feed()

    init {

        binding.layoutNoImageMoveToDetailArea.setOnClickListener {
            onMoveToDetailClick(post.feedId)
        }

        // 게시물 작성자 확인
        binding.ivFeedPostNoImageProperty.setOnClickListener {
            when (post.isAuthor) {
                true -> onMyPostPropertyClick(binding.ivFeedPostNoImageProperty, post.feedId, adapterPosition)
                false -> onOtherPostPropertyClick(binding.ivFeedPostNoImageProperty)
            }
        }

        // 베스트 댓글 작성자 확인
        binding.ivFeedPostNoImageCommentProperty.setOnClickListener {
            when (post.comment.isAuthor) {
                true -> onMyBestCommentPropertyClick(binding.ivFeedPostNoImageCommentProperty, post.comment.commentId, post.comment.content, binding.cardviewFeedPostNoImageComment)
                false -> onOtherBestCommentPropertyClick(binding.ivFeedPostNoImageCommentProperty)
            }
        }

        binding.btnFeedPostNoImageLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedPostNoImageLike,
                post.isLike,
                post.likeCount,
                binding.tvFeedPostNoImageLikeCnt,
                post.feedId,
                null,
                FeedTotalLikeBtnType.POST_LIKE_BTN,
            )
        }

        binding.btnFeedPostNoImageBestCommentLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedPostNoImageBestCommentLike,
                post.comment.isLike,
                post.comment.likeCount,
                binding.tvFeedPostNoImageBestCommentLikeCnt,
                post.feedId,
                post.comment.commentId,
                FeedTotalLikeBtnType.BEST_COMMENT_LIKE_BTN,
            )
        }
    }

    fun bind(item: Feed) {
        post = item

        binding.layoutFeedNoImageComment.isVisible = true

        if (item.commentCount == 0) {
            binding.layoutFeedNoImageComment.isVisible = false
        }

        setFeedPost(item)

        binding.executePendingBindings()
    }

    private fun setFeedPost(item: Feed) {
        binding.tvFeedPostNoImageLikeCnt.text = item.likeCount.toString()
        binding.tvFeedPostNoImageBestCommentLikeCnt.text = item.comment.likeCount.toString()

        binding.tvFeedPostNoImageUserNickname.text = item.author
        binding.tvFeedPostNoImagePostingTime.text = item.createdDate
        binding.tvFeedPostNoImageTitle.text = item.title
        binding.tvFeedPostNoImageContent.text = item.content
        binding.tvFeedPostNoImageCommentCnt.text = item.commentCount.toString()
        binding.tvFeedPostNoImageCommentCommentingTime.text = item.comment.createdDate

        binding.btnFeedPostNoImageLike.showLikeBtnIsLike(item.isLike, binding.btnFeedPostNoImageLike)
        binding.btnFeedPostNoImageBestCommentLike.showLikeBtnIsLike(item.comment.isLike, binding.btnFeedPostNoImageBestCommentLike)

        binding.tvFeedPostNoImageCommentUserNickname.text = item.comment.author
        binding.tvFeedPostNoImageBestCommentContent.text = item.comment.content

        if (!item.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, item.authorProfileImage, binding.ivFeedPostNoImageUserProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedPostNoImageUserProfile, placeHolder = com.beeeam.designsystem.R.drawable.ic_user_base_profile)
        }

        if (!item.comment.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, item.comment.authorProfileImage, binding.ivFeedPostNoImageCommentUserProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedPostNoImageCommentUserProfile, placeHolder = com.beeeam.designsystem.R.drawable.ic_user_base_profile)
        }
    }
}
