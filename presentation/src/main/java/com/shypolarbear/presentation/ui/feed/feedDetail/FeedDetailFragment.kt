package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedDetailBinding
import com.shypolarbear.presentation.databinding.ItemFeedBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedCommentAdapter
import kotlinx.coroutines.NonDisposableHandle.parent
import timber.log.Timber

class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail
) {
    private var isFeedLike = false

    override val viewModel: FeedDetailViewModel by viewModels()

    override fun initView() {

        with(binding.viewpagerFeedDetailImg) {
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

            TabLayoutMediator(binding.tablayoutFeedDetailIndicator, this
            ) { tab, position ->

            }.attach()
        }

        binding.btnFeedDetailLike.setOnClickListener {
            isFeedLike = !isFeedLike
            checkLike(isFeedLike, binding)
        }

        viewModel.loadFeedComment()
        setFeedComment()
    }

    private fun setFeedComment() {
        val feedCommentAdapter = FeedCommentAdapter()
        binding.rvFeedDetailReply.adapter = feedCommentAdapter
        viewModel.feedComment.observe(viewLifecycleOwner) {
            feedCommentAdapter.submitList(it)
        }
    }

    private fun checkLike(isLike: Boolean, binding: FragmentFeedDetailBinding) {

        val likeBtnOn = ContextCompat.getDrawable(binding.root.context, R.drawable.ic_btn_like_on)
        val likeBtnOff = ContextCompat.getDrawable(binding.root.context, R.drawable.ic_btn_like_off)

        if (isLike) {
            binding.btnFeedDetailLike.background = likeBtnOn
        } else {
            binding.btnFeedDetailLike.background = likeBtnOff
        }
    }
}