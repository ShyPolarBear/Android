package com.shypolarbear.presentation.ui.feed

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.PowerMenuUtil
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.powerMenu

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

        binding.feedToolbarSort.setOnClickListener {
            PowerMenuUtil.getPowerMenu(
                requireContext(),
                viewLifecycleOwner,
                feedSortItems
            ) .showAsDropDown(binding.feedToolbarSort, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
        }

        binding.feedPostProperty.setOnClickListener {
            PowerMenuUtil.getPowerMenu(
                requireContext(),
                viewLifecycleOwner,
                feedPostPropertyItems
            ) .showAsDropDown(binding.feedPostProperty, POWER_MENU_OFFSET_X, POWER_MENU_OFFSET_Y)
        }

        setViewPager()
    }

    private fun setViewPager() {
        with(binding.feedPostViewpagerImg) {
            adapter = FeedPostAdapter().apply {
                viewModel.feedPostImgUrl.observe(this@FeedFragment) { imgs ->
                    submitList(imgs)
                }
            }

            TabLayoutMediator(binding.feedPostIndicator, this
            ) { tab, position ->

            }.attach()
        }
    }
}