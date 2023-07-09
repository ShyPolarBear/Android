package com.shypolarbear.presentation.ui.feed.feedTotal

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedTotalBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.FunctionUtil
import com.skydoves.powermenu.PowerMenuItem

class FeedTotalFragment: BaseFragment<FragmentFeedTotalBinding, FeedTotalViewModel> (
    R.layout.fragment_feed_total
){

    companion object {
        const val POWER_MENU_OFFSET_X = -290
        const val POWER_MENU_OFFSET_Y = 0
    }

    override val viewModel: FeedTotalViewModel by viewModels()
    private val feedSortItems: List<PowerMenuItem> by lazy {
        listOf(
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent_best)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_best))
        )
    }

    override fun initView() {
        val functionUtil = FunctionUtil(binding.root.context, feedSortItems, viewLifecycleOwner )

        binding.ivFeedToolbarSort.setOnClickListener {
            functionUtil.setMenu(binding.ivFeedToolbarSort)
        }

        viewModel.loadFeedPost()
        setFeedPost()
    }

    private fun setFeedPost() {
        val feedPostAdapter = FeedPostAdapter(viewLifecycleOwner, childFragmentManager)
        binding.rvFeedPost.adapter = feedPostAdapter
        viewModel.feedPost.observe(viewLifecycleOwner) {
            feedPostAdapter.submitList(it)
        }
    }
}