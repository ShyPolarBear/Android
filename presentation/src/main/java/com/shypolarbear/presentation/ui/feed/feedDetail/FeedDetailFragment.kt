package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedDetailBinding

class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail
) {
    override val viewModel: FeedDetailViewModel by viewModels()

    override fun initView() {

    }
}