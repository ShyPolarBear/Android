package com.shypolarbear.presentation.ui.feed

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.PowerMenuUtil
import com.skydoves.powermenu.PowerMenuItem

class FeedFragment: BaseFragment<FragmentFeedBinding, FeedViewModel> (
    R.layout.fragment_feed
){

    companion object {
        private const val POWER_MENU_OFFSET_X = -290
        private const val POWER_MENU_OFFSET_Y = 0
    }

    override val viewModel: FeedViewModel by viewModels()
    private val feedSortItems: List<PowerMenuItem> by lazy {
        listOf(
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent_best)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_best))
        )
    }

    override fun initView() {

        binding.ivFeedToolbarSort.setOnClickListener {
            PowerMenuUtil.getPowerMenu(
                requireContext(),
                viewLifecycleOwner,
                feedSortItems
            ) .showAsDropDown(binding.ivFeedToolbarSort, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
        }

        viewModel.loadFeedPost()
        setFeedPost()
    }

    private fun setFeedPost() {
        val feedPostAdapter = FeedPostAdapter(viewLifecycleOwner)
        binding.rvFeedPost.adapter = feedPostAdapter
        viewModel.feedPost.observe(viewLifecycleOwner) {
            feedPostAdapter.submitList(it)
        }
    }
}