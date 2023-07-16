package com.shypolarbear.presentation.ui.feed.viewholder

import android.app.Activity
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter

class FeedPostViewHolder(
    private val binding: ItemFeedBinding,
    private val onMyPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherPostPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onMyBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onOtherBestCommentPropertyClick: (view: ImageView) -> Unit = { _ -> },
    private val onBtnLikeClick: (view: Button) -> Unit = { _ -> },
    private val onMoveToDetailClick: () -> Unit = { }
    ) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnFeedPostLike.setOnClickListener {
            onBtnLikeClick(binding.btnFeedPostLike)
        }

        binding.btnFeedPostCommentLike.setOnClickListener {
            onBtnLikeClick(binding.btnFeedPostCommentLike)
        }

        binding.layoutMoveToDetailArea.setOnClickListener {
            onMoveToDetailClick()
        }
    }

    fun bind(post: FeedPost) {
        binding.feedPost = post

        binding.ivFeedPostProperty.setOnClickListener {
            when(post.postOwner) {
                "my" -> onMyPostPropertyClick(binding.ivFeedPostProperty)
                "other" -> onOtherPostPropertyClick(binding.ivFeedPostProperty)
            }
        }

        binding.ivFeedPostCommentProperty.setOnClickListener {
            when(post.bestCommentOwner) {
                "my" -> onMyBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
                "other" -> onOtherBestCommentPropertyClick(binding.ivFeedPostCommentProperty)
            }
        }

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

            binding.executePendingBindings()
        }
    }
}