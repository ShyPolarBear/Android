package com.shypolarbear.presentation.ui.feed.feedWrite

import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedWriteBinding

class FeedWriteFragment: BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel > (
    R.layout.fragment_feed_write
) {

    override val viewModel: FeedWriteViewModel by viewModels()
    private val feedWriteImgAdapter = FeedWriteImgAdapter()

    private val imgTestList: MutableList<FeedWriteImg> = mutableListOf(
        FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
        FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
        FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
        FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
        FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405"),
        FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/9690c7b7-2bde-498c-a5be-886b6e5b5405")
    )

    override fun initView() {
        binding.apply {

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            feedWriteImgAdapter.submitList(imgTestList)

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