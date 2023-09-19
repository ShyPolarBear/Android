package com.shypolarbear.presentation.ui.feed.feedTotal.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalLikeBtnType
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.showLikeBtnIsLike
import timber.log.Timber

class FeedPostViewHolder(
    private val binding: ItemFeedBinding,
    private val onMyPostPropertyClick: (view: ImageView, feedId: Int, position: Int) -> Unit = { _, _, _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        feedId: Int,
        itemType: FeedTotalLikeBtnType
    ) -> Unit = { _, _, _, _, _, _ -> },
    private val onMoveToDetailClick: (feed: Feed, feedId: Int) -> Unit = { _, _ -> }
    ) : RecyclerView.ViewHolder(binding.root) {

    private var post: Feed = Feed()

    init {

        binding.layoutMoveToDetailArea.setOnClickListener {
            onMoveToDetailClick(post, post.feedId)
        }

        // 게시물 작성자 확인
        binding.ivFeedPostProperty.setOnClickListener {
            when(post.isAuthor) {
                true -> onMyPostPropertyClick(binding.ivFeedPostProperty, post.feedId, adapterPosition)
                false -> onOtherPostPropertyClick(binding.ivFeedPostProperty)
            }
        }

        // 베스트 댓글 작성자 확인
        binding.ivFeedPostCommentProperty.setOnClickListener {
            when(post.comment.isAuthor) {
                true -> onMyBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
                false -> onOtherBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
            }
        }

        binding.btnFeedPostLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedPostLike,
                post.isLike,
                post.likeCount,
                binding.tvFeedPostLikeCnt,
                post.feedId,
                FeedTotalLikeBtnType.POST_LIKE_BTN
            )
        }

        binding.btnFeedPostBestCommentLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedPostBestCommentLike,
                post.comment.isLike,
                post.comment.likeCount,
                binding.tvFeedPostBestCommentLikeCnt,
                post.feedId,
                FeedTotalLikeBtnType.BEST_COMMENT_LIKE_BTN
            )
        }
    }

    fun bind(item: Feed) {
        post = item

        binding.layoutFeedComment.isVisible = true

        if (item.commentCount == 0)
            binding.layoutFeedComment.isVisible = false

        setFeedPost(item)
        setFeedPostImg(item)

        binding.executePendingBindings()
    }

    private fun setFeedPost(item: Feed) {

        binding.tvFeedPostLikeCnt.text = item.likeCount.toString()
        binding.tvFeedPostBestCommentLikeCnt.text = item.comment.likeCount.toString()

        binding.tvFeedPostUserNickname.text = item.author
        binding.tvFeedPostPostingTime.text = item.createdDate
        binding.tvFeedPostTitle.text = item.title
        binding.tvFeedPostContent.text = item.content
        binding.tvFeedPostCommentCnt.text = item.commentCount.toString()

        binding.btnFeedPostLike.showLikeBtnIsLike(item.isLike, binding.btnFeedPostLike)
        binding.btnFeedPostBestCommentLike.showLikeBtnIsLike(item.comment.isLike, binding.btnFeedPostBestCommentLike)

        binding.tvFeedPostCommentUserNickname.text = item.comment.author
        binding.tvFeedPostBestCommentContent.text = item.comment.content

        if (!item.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, item.authorProfileImage, binding.ivFeedPostUserProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedPostUserProfile, placeHolder = R.drawable.ic_user_base_profile)
        }

        if (!item.comment.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, item.comment.authorProfileImage, binding.ivFeedPostCommentUserProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedPostCommentUserProfile, placeHolder = R.drawable.ic_user_base_profile)
        }
    }

    private fun setFeedPostImg(post: Feed) {
        with(binding.viewpagerFeedPostImg) {
            adapter = ImageViewPagerAdapter().apply {
                submitList(
                    post.feedImages
                )
            }

            TabLayoutMediator(binding.tablayoutFeedPostIndicator, this
            ) { _, _ ->

            }.attach()
        }
    }
}