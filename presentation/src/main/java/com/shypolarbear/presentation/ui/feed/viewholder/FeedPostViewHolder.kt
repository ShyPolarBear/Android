package com.shypolarbear.presentation.ui.feed.viewholder

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.util.checkLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem

class FeedPostViewHolder(
    private val binding: ItemFeedBinding,
    private val viewLifeCycleOwner: LifecycleOwner,
    ) : RecyclerView.ViewHolder(binding.root) {

    private val feedPostPropertyItems: List<PowerMenuItem> =
        listOf(
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_revise)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_delete)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_report)),
            PowerMenuItem(itemView.context.getString(R.string.feed_post_property_block))
        )
    private var isFeedPostLike = false
    private var isFeedCommentLike = false

    init {

        binding.ivFeedPostProperty.setOnClickListener {
            binding.ivFeedPostProperty.setMenu(
                binding.ivFeedPostProperty,
                feedPostPropertyItems,
                viewLifeCycleOwner
            )
        }

        binding.ivFeedPostCommentProperty.setOnClickListener {
            binding.ivFeedPostCommentProperty.setMenu(
                binding.ivFeedPostCommentProperty,
                feedPostPropertyItems,
                viewLifeCycleOwner
            )
        }

        binding.btnFeedPostLike.setOnClickListener {
            isFeedPostLike = !isFeedPostLike
            binding.btnFeedPostLike.checkLike(isFeedPostLike, binding.btnFeedPostLike)
        }

        binding.btnFeedPostCommentLike.setOnClickListener {
            isFeedCommentLike = !isFeedCommentLike
            binding.btnFeedPostLike.checkLike(isFeedCommentLike, binding.btnFeedPostCommentLike)
        }

        binding.layoutMoveToDetailArea.setOnClickListener {
            val context = itemView.context
            findNavController(context as Activity, R.id.nav_host)
                .navigate(R.id.action_feedTotalFragment_to_feedDetailFragment)
        }
    }

    fun bind(post: FeedPost) {
        binding.feedPost = post
        binding.executePendingBindings()

        with(binding.viewpagerFeedPostImg) {
            adapter = ImageViewPagerAdapter().apply {
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
}