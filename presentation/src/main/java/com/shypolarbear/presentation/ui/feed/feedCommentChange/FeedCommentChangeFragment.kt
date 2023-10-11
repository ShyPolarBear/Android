package com.shypolarbear.presentation.ui.feed.feedCommentChange

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedCommentChangeBinding
import com.shypolarbear.presentation.ui.feed.feedDetail.FeedDetailFragmentArgs
import com.shypolarbear.presentation.ui.feed.feedTotal.FragmentTotalStatus
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADED
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADING
import dagger.hilt.android.AndroidEntryPoint

const val UPLOADING = 0
const val UPLOADED = 1

@AndroidEntryPoint
class FeedCommentChangeFragment: BaseFragment<FragmentFeedCommentChangeBinding, FeedCommentChangeViewModel>(
    R.layout.fragment_feed_comment_change
) {

    override val viewModel: FeedCommentChangeViewModel by viewModels()
    private val feedCommentChangeArgs: FeedCommentChangeFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            edtFeedCommentChangeContent.setText(feedCommentChangeArgs.content)

            btnFeedCommentChangeBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnFeedCommentChangeConfirm.setOnClickListener {
                when {
                    edtFeedCommentChangeContent.text.toString().isNullOrBlank() -> {
                        Toast.makeText(requireContext(), getString(R.string.feed_write_content_msg), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        viewModel.changeComment(feedCommentChangeArgs.commentId, edtFeedCommentChangeContent.text.toString())
                    }
                }
            }

            viewModel.uploadState.observe(viewLifecycleOwner) {
                when(viewModel.uploadState.value) {
                    UPLOADING -> { }
                    UPLOADED -> { findNavController().popBackStack() }
                }
            }
        }
    }
}