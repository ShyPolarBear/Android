package com.shypolarbear.presentation.ui.feed

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding
import com.shypolarbear.presentation.util.PowerMenuUtil
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.powerMenu

class FeedFragment: BaseFragment<FragmentFeedBinding, FeedViewModel> (
    R.layout.fragment_feed
){
    override val viewModel: FeedViewModel by viewModels()
    private val feedSortItems: List<PowerMenuItem> = listOf(PowerMenuItem("최신"), PowerMenuItem("최근 인기"),PowerMenuItem("best"))

    override fun initView() {

        binding.feedToolbarSort.setOnClickListener {

            PowerMenuUtil.getPowerMenu(
                requireContext(),
                viewLifecycleOwner,
                feedSortItems
            ) .showAsDropDown(binding.feedToolbarSort, -290, 0)
        }
    }
}