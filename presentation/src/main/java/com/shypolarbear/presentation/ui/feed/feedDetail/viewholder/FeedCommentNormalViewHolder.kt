package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.feedDetail.CommentData
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedCommentAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedReplyAdapter
import com.shypolarbear.presentation.util.showLike
import timber.log.Timber

class FeedCommentNormalViewHolder (
    private val binding: ItemFeedCommentNormalBinding,
    private val onMyCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button, isLiked: Boolean, likeCnt: Int, textView: TextView) -> Int = { _, _, _, _ -> 0},
    ) : RecyclerView.ViewHolder(binding.root) {

    private val feedReplyAdapter: FeedReplyAdapter by lazy {
        FeedReplyAdapter(
            onMyReplyPropertyClick = onMyReplyPropertyClick,
            onOtherReplyPropertyClick = onOtherReplyPropertyClick,
            onBtnLikeClick = onBtnLikeClick
        )
    }

    init {
        binding.btnFeedCommentNormalLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedCommentNormalLike,
                true,
                10,
                binding.tvFeedCommentNormalLikeCnt)
        }
    }

    fun bind(comment: Comment) {
        // Todo(일반 댓글)

        when(comment.isAuthor) {
            true -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    onMyCommentPropertyClick(binding.ivFeedCommentNormalProperty)
                }
            }

            false -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    onOtherCommentPropertyClick(binding.ivFeedCommentNormalProperty)
                }
            }
        }

        setLikeBtn(comment)
        setComment(comment)

        binding.executePendingBindings()
    }

    private fun setComment(comment: Comment) {
        binding.rvFeedCommentReply.adapter = feedReplyAdapter
        feedReplyAdapter.submitList(comment.childComments)

        binding.tvFeedCommentNormalNickname.text = comment.author
        binding.tvFeedCommentNormalContent.text = comment.content
        binding.tvFeedCommentNormalTime.text = comment.createdDate

        Timber.d(comment.toString())
        if (comment.authorProfileImage != "") {

            Glide.with(itemView)
                .load(comment.authorProfileImage)
                .into(binding.ivFeedCommentNormalProfile)
        }
    }

    private fun setLikeBtn(comment: Comment) {
        var isCommentLike = comment.isLike
        var commentLikeCnt: Int = comment.likeCount

        binding.btnFeedCommentNormalLike.showLike(comment.isLike, binding.btnFeedCommentNormalLike)
        binding.tvFeedCommentNormalLikeCnt.text = comment.likeCount.toString()

        binding.btnFeedCommentNormalLike.setOnClickListener {
            commentLikeCnt = onBtnLikeClick(
                binding.btnFeedCommentNormalLike,
                isCommentLike,
                commentLikeCnt,
                binding.tvFeedCommentNormalLikeCnt
            )
            isCommentLike = !isCommentLike
        }
    }
}