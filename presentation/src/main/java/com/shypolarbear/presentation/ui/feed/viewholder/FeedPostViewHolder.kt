package com.shypolarbear.presentation.ui.feed.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
    private val onMoveToDetailClick: () -> Unit = { }
    ) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.layoutMoveToDetailArea.setOnClickListener {
            onMoveToDetailClick()
        }
    }

    fun bind(post: Feed) {
        var isPostLike = post.isLike
        var isCommentLike = post.bestComment.isLike
        var isPostLikeCnt: Int = post.likeCount.toInt()
        var isCommentLikeCnt = post.bestComment.likeCount

        // 게시물 작성자 확인
        binding.ivFeedPostProperty.setOnClickListener {
            when(post.isAuthor) {
                true -> onMyPostPropertyClick(binding.ivFeedPostProperty)
                false -> onOtherPostPropertyClick(binding.ivFeedPostProperty)
            }
        }

        // 베스트 댓글 작성자 확인
        binding.ivFeedPostCommentProperty.setOnClickListener {
            when(post.bestComment.isAuthor) {
                true -> onMyBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
                false -> onOtherBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
            }
        }

        binding.btnFeedPostLike.showLike(post.isLike, binding.btnFeedPostLike)
        binding.btnFeedPostBestCommentLike.showLike(post.bestComment.isLike, binding.btnFeedPostBestCommentLike)

        binding.btnFeedPostLike.setOnClickListener {
            isPostLikeCnt = onBtnLikeClick(
                binding.btnFeedPostLike,
                isPostLike,
                isPostLikeCnt,
                binding.tvFeedPostLikeCnt
            )
            isPostLike = !isPostLike
        }

        binding.btnFeedPostBestCommentLike.setOnClickListener {
            isCommentLikeCnt = onBtnLikeClick(
                binding.btnFeedPostBestCommentLike,
                isCommentLike,
                isCommentLikeCnt,
                binding.tvFeedPostBestCommentLikeCnt
            )
            isCommentLike = !isCommentLike
        }

        binding.tvFeedPostLikeCnt.text = post.likeCount
        binding.tvFeedPostBestCommentLikeCnt.text = post.bestComment.likeCount.toString()

        binding.tvFeedPostUserNickname.text = post.author
        binding.tvFeedPostPostingTime.text = post.createdDate
        binding.tvFeedPostTitle.text = post.title
        binding.tvFeedPostContent.text = post.content
        binding.tvFeedPostCommentCnt.text = post.commentCount.toString()

        binding.tvFeedPostCommentUserNickname.text = post.bestComment.author
        binding.tvFeedPostBestCommentContent.text = post.bestComment.content

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