package com.shypolarbear.presentation.ui.feed.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
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

    fun bind(post: Feed) {
        var isPostLike = post.isLike
        var isCommentLike = post.comment.isLike
        var postLikeCnt: Int = post.likeCount
        var commentLikeCnt = post.comment.likeCount

        if (post.commentCount == 0) {
            binding.layoutFeedComment.isVisible = false
        }

        binding.layoutMoveToDetailArea.setOnClickListener {
            onMoveToDetailClick(post.feedId)
        }

        // 게시물 작성자 확인
        binding.ivFeedPostProperty.setOnClickListener {
            when(post.isAuthor) {
                true -> onMyPostPropertyClick(binding.ivFeedPostProperty)
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

        binding.btnFeedPostLike.showLike(post.isLike, binding.btnFeedPostLike)
        binding.btnFeedPostBestCommentLike.showLike(post.comment.isLike, binding.btnFeedPostBestCommentLike)

        binding.btnFeedPostLike.setOnClickListener {
            postLikeCnt = onBtnLikeClick(
                binding.btnFeedPostLike,
                isPostLike,
                postLikeCnt,
                binding.tvFeedPostLikeCnt
            )
            isPostLike = !isPostLike
        }

        binding.btnFeedPostBestCommentLike.setOnClickListener {
            commentLikeCnt = onBtnLikeClick(
                binding.btnFeedPostBestCommentLike,
                isCommentLike,
                commentLikeCnt,
                binding.tvFeedPostBestCommentLikeCnt
            )
            isCommentLike = !isCommentLike
        }

        binding.tvFeedPostLikeCnt.text = post.likeCount.toString()
        binding.tvFeedPostBestCommentLikeCnt.text = post.comment.likeCount.toString()

        binding.tvFeedPostUserNickname.text = post.author
        binding.tvFeedPostPostingTime.text = post.createdDate
        binding.tvFeedPostTitle.text = post.title
        binding.tvFeedPostContent.text = post.content
        binding.tvFeedPostCommentCnt.text = post.commentCount.toString()

        binding.tvFeedPostCommentUserNickname.text = post.comment.author
        binding.tvFeedPostBestCommentContent.text = post.comment.content

        with(binding.viewpagerFeedPostImg) {
            adapter = ImageViewPagerAdapter().apply {
                submitList(
                    // 테스트 데이터
                    post.feedImage
                )
            }

            TabLayoutMediator(binding.tablayoutFeedPostIndicator, this
            ) { tab, position ->

            }.attach()
        }

        binding.executePendingBindings()
    }
}