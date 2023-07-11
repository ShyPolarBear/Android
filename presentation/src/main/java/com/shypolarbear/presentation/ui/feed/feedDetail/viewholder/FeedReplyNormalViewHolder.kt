package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedReplyNormalBinding
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem

class FeedReplyNormalViewHolder (
    private val binding: ItemFeedReplyNormalBinding,
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

    fun bind(comment: FeedComment) {
        // Todo(일반 대댓글)

        when(comment.owner) {
            "my" -> {
                binding.ivFeedReplyNormalProperty.setOnClickListener {
                    binding.ivFeedReplyNormalProperty.setMenu(
                        binding.ivFeedReplyNormalProperty,
                        myCommentPropertyItems,
                        viewLifeCycleOwner
                    )
                }
            }

            "other" -> {
                binding.ivFeedReplyNormalProperty.setOnClickListener {
                    binding.ivFeedReplyNormalProperty.setMenu(
                        binding.ivFeedReplyNormalProperty,
                        otherCommentPropertyItems,
                        viewLifeCycleOwner
                    )
                }
            }
        }

        binding.executePendingBindings()
    }
}