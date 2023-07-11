package com.shypolarbear.presentation.ui.feed.feedDetail.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.feed.feedDetail.FeedComment
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedCommentNormalBinding
import com.shypolarbear.presentation.util.FunctionUtil
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

    fun bind(comment: FeedComment) {
        // Todo(일반 댓글)

        val functionUtil1 = FunctionUtil(binding.root.context, myCommentPropertyItems, viewLifeCycleOwner )
        val functionUtil2 = FunctionUtil(binding.root.context, otherCommentPropertyItems, viewLifeCycleOwner )

        when(comment.owner) {
            "my" -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    functionUtil1.setMenu(binding.ivFeedCommentNormalProperty)
                }
            }

            "other" -> {
                binding.ivFeedCommentNormalProperty.setOnClickListener {
                    functionUtil2.setMenu(binding.ivFeedCommentNormalProperty)
                }
            }
        }

        binding.executePendingBindings()
    }
}