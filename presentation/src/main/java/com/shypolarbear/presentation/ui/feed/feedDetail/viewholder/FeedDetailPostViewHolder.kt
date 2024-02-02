package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedDetailPostBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailLikeBtnType
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.showLikeBtnIsLike

class FeedDetailPostViewHolder(
    private val binding: ItemFeedDetailPostBinding,
    private val onPostPropertyClick: (Feed, ImageView) -> Unit = { _, _ -> },
    private val onBtnLikeClick: (
        view: Button,
        isLiked: Boolean,
        likeCnt: Int,
        textView: TextView,
        commentId: Int,
        replyId: Int,
        itemType: FeedDetailLikeBtnType,
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    private val onBtnBackClick: () -> Unit = {},
) : RecyclerView.ViewHolder(binding.root) {

    private var post: Feed = Feed()

    init {
        binding.apply {
            btnFeedDetailLike.setOnClickListener {
                onBtnLikeClick(
                    btnFeedDetailLike,
                    post.isLike,
                    post.likeCount,
                    tvFeedDetailLikeCnt,
                    0,
                    0,
                    FeedDetailLikeBtnType.POST_LIKE_BTN,
                )
            }

            ivFeedDetailProperty.setOnClickListener {
                onPostPropertyClick(post, ivFeedDetailProperty)
            }

            btnFeedDetailBack.setOnClickListener {
                onBtnBackClick()
            }
        }
    }

    fun bind(item: Feed) {
        post = item

        binding.apply {
            setFeedPost(item)
            executePendingBindings()
        }
    }

    private fun setFeedPost(feedDetail: Feed) {
        binding.tvFeedDetailUserNickname.text = feedDetail.author
        binding.tvFeedDetailPostingTime.text = feedDetail.createdDate
        binding.tvFeedDetailLikeCnt.text = feedDetail.likeCount.toString()
        binding.tvFeedDetailTitle.text = feedDetail.title
        binding.tvFeedDetailContent.text = feedDetail.content
        binding.tvFeedDetailReplyCnt.text = feedDetail.commentCount.toString()

        binding.btnFeedDetailLike.showLikeBtnIsLike(feedDetail.isLike, binding.btnFeedDetailLike)

        if (!feedDetail.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, feedDetail.authorProfileImage, binding.ivFeedDetailUserProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedDetailUserProfile, placeHolder = R.drawable.ic_user_base_profile)
        }

        with(binding.viewpagerFeedDetailImg) {
            adapter = ImageViewPagerAdapter().apply {
                submitList(feedDetail.feedImages)
            }

            TabLayoutMediator(
                binding.tablayoutFeedDetailIndicator,
                this,
            ) { _, _ ->
            }.attach()
        }
    }
}
