package com.shypolarbear.presentation.ui.feed.viewholder

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostImgAdapter
import com.shypolarbear.presentation.util.PowerMenuUtil
import com.skydoves.powermenu.PowerMenuItem

class FeedPostViewHolder(
    private val binding: ItemFeedBinding,
    private val viewLifeCycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val POWER_MENU_OFFSET_X = -290
        private const val POWER_MENU_OFFSET_Y = 0
    }

    private val feedPostPropertyItems: List<PowerMenuItem> =
        listOf(
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_revise)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_delete)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_report)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_block))
        )
    private var IS_FEED_LIKE = false
    private var IS_FEED_REPLY_LIKE = false

    init {
        binding.ivFeedPostProperty.setOnClickListener {
            PowerMenuUtil.getPowerMenu(
                binding.root.context,
                viewLifeCycleOwner,
                feedPostPropertyItems
            ) .showAsDropDown(binding.ivFeedPostProperty, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
        }

        binding.ivFeedPostReplyProperty.setOnClickListener {
            PowerMenuUtil.getPowerMenu(
                binding.root.context,
                viewLifeCycleOwner,
                feedPostPropertyItems
            ) .showAsDropDown(binding.ivFeedPostReplyProperty, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
        }

        binding.btnFeedPostLike.setOnClickListener {
            IS_FEED_LIKE = !IS_FEED_LIKE
            checkLike(IS_FEED_LIKE, binding, "post")
        }

        binding.btnFeedPostReplyLike.setOnClickListener {
            IS_FEED_REPLY_LIKE = !IS_FEED_REPLY_LIKE
            checkLike(IS_FEED_REPLY_LIKE, binding, "reply")
        }
    }

    fun bind(post: FeedPost) {
        binding.feedPost = post
        binding.executePendingBindings()

        with(binding.viewpagerFeedPostImg) {
            adapter = FeedPostImgAdapter().apply {
                submitList(
                    // 테스트 데이터
                    mutableListOf(
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/4eed9944-8689-442e-87d7-c4ac6a939103"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/083a3008-642b-42b8-9845-6696aa641e31"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/4c0f2ca6-defc-455c-b384-ba573f72a981"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/24b65d18-006e-41ee-8440-2a5f00706028")
                    )
                )
            }

            TabLayoutMediator(binding.tablayoutFeedPostIndicator, this
            ) { tab, position ->

            }.attach()
        }
    }

    private fun checkLike(isLike: Boolean, binding: ItemFeedBinding, diff: String) {

        val likeBtnOn = ContextCompat.getDrawable(binding.root.context, R.drawable.ic_btn_like_on)
        val likeBtnOff = ContextCompat.getDrawable(binding.root.context, R.drawable.ic_btn_like_off)

        when (diff) {
            "post" -> {
                if (isLike) {
                    binding.btnFeedPostLike.background = likeBtnOn
                } else {
                    binding.btnFeedPostLike.background = likeBtnOff
                }
            }

            "reply" -> {
                if (isLike) {
                    binding.btnFeedPostReplyLike.background = likeBtnOn
                } else {
                    binding.btnFeedPostReplyLike.background = likeBtnOff
                }
            }
        }
    }
}