package com.shypolarbear.presentation.ui.feed.feedWrite

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedWriteBinding

class FeedWriteFragment: BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel > (
    R.layout.fragment_feed_write
) {

    override val viewModel: FeedWriteViewModel by viewModels()

    override fun initView() {
        binding.apply {
            btnFeedWriteBack.setOnClickListener {
                findNavController().navigate(R.id.action_feedWriteFragment_to_navigation_feed)
            }

            btnFeedWriteConfirm.setOnClickListener {
                if (edtFeedWriteTitle.text.toString().isBlank())
                    Toast.makeText(requireContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                else if (edtFeedWriteContent.text.toString().isBlank())
                    Toast.makeText(requireContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                else
                    findNavController().navigate(R.id.action_feedWriteFragment_to_navigation_feed)


            }
        }
    }
}