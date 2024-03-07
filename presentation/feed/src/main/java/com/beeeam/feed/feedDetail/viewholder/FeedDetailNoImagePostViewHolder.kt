package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.feed.databinding.ItemFeedDetailNoImagePostBinding
import com.beeeam.util.FeedDetailLikeBtnType
import com.beeeam.util.GlideUtil
import com.beeeam.util.showLikeBtnIsLike
import com.shypolarbear.domain.model.feed.Feed

class FeedDetailNoImagePostViewHolder(
    private val binding: ItemFeedDetailNoImagePostBinding,
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
            btnFeedDetailNoImageLike.setOnClickListener {
                onBtnLikeClick(
                    btnFeedDetailNoImageLike,
                    post.isLike,
                    post.likeCount,
                    tvFeedDetailNoImageLikeCnt,
                    0,
                    0,
                    FeedDetailLikeBtnType.POST_LIKE_BTN,
                )
            }

            ivFeedDetailNoImageProperty.setOnClickListener {
                onPostPropertyClick(post, ivFeedDetailNoImageProperty)
            }

            btnFeedDetailNoImageBack.setOnClickListener {
                onBtnBackClick()
            }
        }
    }

    fun bind(item: Feed) {
        post = item

        setFeedPost(item)
        binding.executePendingBindings()
    }

    private fun setFeedPost(feedDetail: Feed) {
        binding.tvFeedDetailNoImageUserNickname.text = feedDetail.author
        binding.tvFeedDetailNoImagePostingTime.text = feedDetail.createdDate
        binding.tvFeedDetailNoImageLikeCnt.text = feedDetail.likeCount.toString()
        binding.tvFeedDetailNoImageTitle.text = feedDetail.title
        binding.tvFeedDetailNoImageContent.text = feedDetail.content
        binding.tvFeedDetailNoImageReplyCnt.text = feedDetail.commentCount.toString()

        binding.btnFeedDetailNoImageLike.showLikeBtnIsLike(feedDetail.isLike, binding.btnFeedDetailNoImageLike)

        if (!feedDetail.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(itemView.context, feedDetail.authorProfileImage, binding.ivFeedDetailNoImageUserProfile)
        } else {
            GlideUtil.loadImage(itemView.context, url = null, view = binding.ivFeedDetailNoImageUserProfile, placeHolder = com.beeeam.designsystem.R.drawable.ic_user_base_profile)
        }
    }
}
