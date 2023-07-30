package com.shypolarbear.presentation.ui.feed.feedTotal.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.util.showLike
import timber.log.Timber

class FeedPostViewHolder(
    private val binding: ItemFeedBinding,
    private val onMyPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button, isLiked: Boolean, likeCnt: Int, textView: TextView) -> Int = { _, _, _, _ -> 0},
    private val onMoveToDetailClick: (feedId: Int) -> Unit = { }
    ) : RecyclerView.ViewHolder(binding.root) {

    private var po: Feed = Feed()

    init {
        var postBtnClicked = false
        var commentBtnClicked = false
        var isPostLike = false
        var isCommentLike = false
        var postLikeCnt: Int = 0
        var commentLikeCnt = 0

        binding.layoutMoveToDetailArea.setOnClickListener {
            onMoveToDetailClick(po.feedId)
        }

        // 게시물 작성자 확인
        binding.ivFeedPostProperty.setOnClickListener {
            when(po.isAuthor) {
                true -> onMyPostPropertyClick(binding.ivFeedPostProperty)
                false -> onOtherPostPropertyClick(binding.ivFeedPostProperty)
            }
        }

        // 베스트 댓글 작성자 확인
        binding.ivFeedPostCommentProperty.setOnClickListener {
            when(po.comment.isAuthor) {
                true -> onMyBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
                false -> onOtherBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
            }
        }

        binding.btnFeedPostLike.setOnClickListener {
            when(postBtnClicked) {
                true -> {
                    isPostLike = isPostLike
                    postLikeCnt = postLikeCnt
                }
                false -> {
                    isPostLike = po.isLike
                    postLikeCnt= po.likeCount
                }
            }
            postLikeCnt = onBtnLikeClick(
                binding.btnFeedPostLike,
                isPostLike,
                postLikeCnt,
                binding.tvFeedPostLikeCnt
            )
            isPostLike = !isPostLike
            postBtnClicked = true
        }

        binding.btnFeedPostBestCommentLike.setOnClickListener {
            when(commentBtnClicked) {
                true -> {
                    isCommentLike = isCommentLike
                    commentLikeCnt = commentLikeCnt
                }
                false -> {
                    isCommentLike = po.comment.isLike
                    commentLikeCnt= po.comment.likeCount
                }
            }
            commentLikeCnt = onBtnLikeClick(
                binding.btnFeedPostBestCommentLike,
                isCommentLike,
                commentLikeCnt,
                binding.tvFeedPostBestCommentLikeCnt
            )
            isCommentLike = !isCommentLike
            commentBtnClicked = true
        }
    }

    fun bind(post: Feed) {
        po = post

        if (post.commentCount == 0) {
            binding.layoutFeedComment.isVisible = false
        }

        setFeedPost(post)
        setFeedPostImg(post)

        binding.executePendingBindings()
    }

    private fun setFeedPost(post: Feed) {

        if (!post.authorProfileImage.isNullOrBlank()) {
            Glide.with(itemView)
                .load(post.authorProfileImage)
                .into(binding.ivFeedPostUserProfile)
        }

        if (!post.comment.authorProfileImage.isNullOrBlank()) {
            Glide.with(itemView)
                .load(post.comment.authorProfileImage)
                .into(binding.ivFeedPostCommentUserProfile)
        }

        binding.tvFeedPostLikeCnt.text = post.likeCount.toString()
        binding.tvFeedPostBestCommentLikeCnt.text = post.comment.likeCount.toString()

        binding.tvFeedPostUserNickname.text = post.author
        binding.tvFeedPostPostingTime.text = post.createdDate
        binding.tvFeedPostTitle.text = post.title
        binding.tvFeedPostContent.text = post.content
        binding.tvFeedPostCommentCnt.text = post.commentCount.toString()

        binding.btnFeedPostLike.showLike(post.isLike, binding.btnFeedPostLike)
        binding.btnFeedPostBestCommentLike.showLike(post.comment.isLike, binding.btnFeedPostBestCommentLike)

        binding.tvFeedPostCommentUserNickname.text = post.comment.author
        binding.tvFeedPostBestCommentContent.text = post.comment.content
    }

    private fun setFeedPostImg(post: Feed) {
        with(binding.viewpagerFeedPostImg) {
            adapter = ImageViewPagerAdapter().apply {
                submitList(
                    post.feedImage
                )
            }

            TabLayoutMediator(binding.tablayoutFeedPostIndicator, this
            ) { tab, position ->

            }.attach()
        }
    }
}