package com.shypolarbear.presentation.ui.feed.feedDetail

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedDetailBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedCommentAdapter
import kotlinx.coroutines.NonDisposableHandle.parent

class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail
) {
    override val viewModel: FeedDetailViewModel by viewModels()

    override fun initView() {

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
}