package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.FeedPostImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedDetailBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedCommentAdapter
import com.shypolarbear.presentation.util.checkLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem

class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail
) {

    override val viewModel: FeedDetailViewModel by viewModels()

    private var isFeedLike = false
    private val postPropertyItems: List<PowerMenuItem> by lazy {
        listOf(
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_block))
        )
    }

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

        binding.ivFeedDetailProperty.setOnClickListener {
            binding.ivFeedDetailProperty.setMenu(
                binding.ivFeedDetailProperty,
                postPropertyItems,
                viewLifecycleOwner
            )
        }

        binding.btnFeedDetailLike.setOnClickListener {
            isFeedLike = !isFeedLike
            binding.btnFeedDetailLike.checkLike(isFeedLike, binding.btnFeedDetailLike)
        }

        binding.btnFeedDetailBack.setOnClickListener {
            findNavController().navigate(R.id.action_feedDetailFragment_to_feedTotalFragment)
        }

        binding.edtFeedDetailReply.setOnFocusChangeListener { _, isFocus ->
            binding.cardviewFeedCommentWritingMsg.isVisible = isFocus
        }

        binding.layoutFeedDetail.setOnClickListener {
            binding.edtFeedDetailReply.clearFocus()
        }

        binding.btnFeedCommentWritingClose.setOnClickListener {
            binding.cardviewFeedCommentWritingMsg.isVisible = false
        }

        viewModel.loadFeedComment()
        setFeedComment()
    }

    private fun setFeedComment() {
        val feedCommentAdapter = FeedCommentAdapter(viewLifecycleOwner)
        binding.rvFeedDetailReply.adapter = feedCommentAdapter
        viewModel.feedComment.observe(viewLifecycleOwner) {
            feedCommentAdapter.submitList(it)
        }
    }
}