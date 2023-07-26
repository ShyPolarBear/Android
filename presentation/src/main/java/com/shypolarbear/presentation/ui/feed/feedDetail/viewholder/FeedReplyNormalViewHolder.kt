package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.feedDetail.ChildComment
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding
import com.shypolarbear.presentation.util.showLike

class FeedReplyNormalViewHolder (
    private val binding: ItemFeedReplyNormalBinding,
    private val onMyReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherReplyPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button, isLiked: Boolean, likeCnt: Int, textView: TextView) -> Int = { _, _, _, _ -> 0}
    ) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnFeedReplyNormalLike.setOnClickListener {
            onBtnLikeClick(
                binding.btnFeedReplyNormalLike,
                // 테스트 데이터
                true,
                10,
                binding.tvFeedReplyNormalLikeCnt)
        }
    }

    fun bind(reply: ChildComment) {
        // Todo(일반 대댓글)

        when(reply.isAuthor) {
            true -> {
                binding.ivFeedReplyNormalProperty.setOnClickListener {
                    onMyReplyPropertyClick(binding.ivFeedReplyNormalProperty)
                }
            }

            false -> {
                binding.ivFeedReplyNormalProperty.setOnClickListener {
                    onOtherReplyPropertyClick(binding.ivFeedReplyNormalProperty)
                }
            }
        }

        setReply(reply)
        setLikeBtn(reply)

        binding.executePendingBindings()
    }

    private fun setReply(reply: ChildComment) {
        binding.tvFeedReplyNormalNickname.text = reply.author
        binding.tvFeedReplyNormalContent.text = reply.content
        binding.tvFeedReplyNormalTime.text = reply.createdDate
    }

    private fun setLikeBtn(reply: ChildComment) {
        var isReplyLike = reply.isLike
        var replyLikeCnt: Int = reply.likeCount

        binding.btnFeedReplyNormalLike.showLike(reply.isLike, binding.btnFeedReplyNormalLike)
        binding.tvFeedReplyNormalLikeCnt.text = reply.likeCount.toString()

        binding.btnFeedReplyNormalLike.setOnClickListener {
            replyLikeCnt = onBtnLikeClick(
                binding.btnFeedReplyNormalLike,
                isReplyLike,
                replyLikeCnt,
                binding.tvFeedReplyNormalLikeCnt
            )
            isReplyLike = !isReplyLike
        }
    }
}