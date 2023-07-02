package com.shypolarbear.presentation.ui.feed.viewholder

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedPostImg
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

    private val feedPostPropertyItems: List<PowerMenuItem> = listOf(PowerMenuItem("수정"), PowerMenuItem("삭제"), PowerMenuItem("신고"), PowerMenuItem("차단"))

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
    }

    fun bind(post: FeedPost) {
        binding.feedPost = post
        binding.executePendingBindings()

        with(binding.viewpagerFeedPostImg) {
            adapter = FeedPostImgAdapter().apply {
                submitList(
                    mutableListOf(
                        // 테스트 데이터
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
                        FeedPostImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405")
                    )
                )
            }

            TabLayoutMediator(binding.tablayoutFeedPostIndicator, this
            ) { tab, position ->

            }.attach()
        }
    }
}