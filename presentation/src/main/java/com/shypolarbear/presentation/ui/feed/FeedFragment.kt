package com.shypolarbear.presentation.ui.feed

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding

class FeedFragment: BaseFragment<FragmentFeedBinding, FeedViewModel>(
    R.layout.fragment_feed
) {

    override val viewModel: FeedViewModel by viewModels()

    override fun initView() {
//        findNavController().navigate(R.id.action_feedFragment_to_feedTotalFragment)
    }

}