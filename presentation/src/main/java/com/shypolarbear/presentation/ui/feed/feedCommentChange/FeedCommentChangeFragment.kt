package com.shypolarbear.presentation.ui.feed.feedCommentChange

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedCommentChangeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedCommentChangeFragment: BaseFragment<FragmentFeedCommentChangeBinding, FeedCommentChangeViewModel>(
    R.layout.fragment_feed_comment_change
) {

    override val viewModel: FeedCommentChangeViewModel by viewModels()

    override fun initView() {
        binding.apply {
            btnFeedCommentChangeBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}