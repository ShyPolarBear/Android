package com.shypolarbear.presentation.ui.feed

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailFragment

class FeedFragment: BaseFragment<FragmentFeedBinding, FeedViewModel>(
    R.layout.fragment_feed
) {

    override val viewModel: FeedViewModel by viewModels()

    override fun initView() {
        childFragmentManager.beginTransaction()
            .replace(R.id.feed, FeedDetailFragment())
            .addToBackStack(null)
            .commit()
    }

}