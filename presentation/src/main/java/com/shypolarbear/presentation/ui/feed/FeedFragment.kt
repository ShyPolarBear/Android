package com.shypolarbear.presentation.ui.feed

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostImgAdapter
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
    private val feedSortItems: List<PowerMenuItem> = listOf(PowerMenuItem("최신"), PowerMenuItem("최근 인기"), PowerMenuItem("best"))
    private val feedPostPropertyItems: List<PowerMenuItem> = listOf(PowerMenuItem("수정"), PowerMenuItem("삭제"), PowerMenuItem("신고"), PowerMenuItem("차단"))

    override fun initView() {

        binding.ivFeedToolbarSort.setOnClickListener {
            PowerMenuUtil.getPowerMenu(
                requireContext(),
                viewLifecycleOwner,
                feedSortItems
            ) .showAsDropDown(binding.ivFeedToolbarSort, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
        }

//        binding.ivFeedPostProperty.setOnClickListener {
//            PowerMenuUtil.getPowerMenu(
//                requireContext(),
//                viewLifecycleOwner,
//                feedPostPropertyItems
//            ) .showAsDropDown(binding.ivFeedPostProperty, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
//        }
//
//        binding.ivFeedPostReplyProperty.setOnClickListener {
//            PowerMenuUtil.getPowerMenu(
//                requireContext(),
//                viewLifecycleOwner,
//                feedPostPropertyItems
//            ) .showAsDropDown(binding.ivFeedPostReplyProperty, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
//        }

        viewModel.loadFeedPost()
        setFeedPost()
    }

    private fun setFeedPost() {
        val feedPostAdapter = FeedPostAdapter()
        binding.rvFeedPost.adapter = feedPostAdapter
        viewModel.feedPost.observe(viewLifecycleOwner) {
            feedPostAdapter.submitList(it)
        }
    }
}