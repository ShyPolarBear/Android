package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.util.checkLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem

class FeedCommentNormalViewHolder (
    private val binding: ItemFeedCommentNormalBinding,
    private val viewLifeCycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root) {

    private val myCommentPropertyItems: List<PowerMenuItem> =
        listOf(
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_revise)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_delete)),
            PowerMenuItem(itemView.context.getString(R.string.feed_comment_reply))
        )

    private val otherCommentPropertyItems: List<PowerMenuItem> =
        listOf(
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_report)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_block)),
            PowerMenuItem(itemView.context.getString(R.string.feed_comment_reply))
        )

    private var isCommentLike = false

    init {
        binding.btnFeedCommentNormalLike.setOnClickListener {
            isCommentLike = !isCommentLike
            binding.btnFeedCommentNormalLike.checkLike(isCommentLike, binding.btnFeedCommentNormalLike)
        }
    }

    fun bind(comment: FeedComment) {
        // Todo(일반 댓글)

        when(comment.owner) {
            "my" -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    binding.ivFeedCommentNormalProperty.setMenu(
                        binding.ivFeedCommentNormalProperty,
                        myCommentPropertyItems,
                        viewLifeCycleOwner
                    )
                }
            }

            "other" -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    binding.ivFeedCommentNormalProperty.setMenu(
                        binding.ivFeedCommentNormalProperty,
                        otherCommentPropertyItems,
                        viewLifeCycleOwner
                    )
                }
            }
        }

        binding.executePendingBindings()
    }
}