package com.shypolarbear.presentation.ui.feed.feedWrite

import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedWriteBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

//@AndroidEntryPoint
class FeedWriteFragment: BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel > (
    R.layout.fragment_feed_write
) {

    override val viewModel: FeedWriteViewModel by viewModels()
    private val feedWriteImgAdapter = FeedWriteImgAdapter(
        onRemoveImgClick = { position: Int -> removeImg(position) }
    )

    // 임시 데이터
    private var imgTestList: MutableList<FeedWriteImg> = mutableListOf(
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

            btnFeedWriteAddPhoto.setOnClickListener {
                addImg()
            }

            btnFeedWriteConfirm.setOnClickListener {
                if (edtFeedWriteTitle.text.toString().isBlank())
                    Toast.makeText(requireContext(), getString(R.string.feed_write_title_msg), Toast.LENGTH_SHORT).show()
                else if (edtFeedWriteContent.text.toString().isBlank())
                    Toast.makeText(requireContext(), getString(R.string.feed_write_content_msg), Toast.LENGTH_SHORT).show()
                else
                    findNavController().navigate(R.id.action_feedWriteFragment_to_navigation_feed)

            }
        }
    }

    private fun addImg() {
        imgTestList.add(0, FeedWriteImg("https://github.com/ShyPolarBear/Android/assets/107917980/30b3d3c8-f2d8-4760-9912-faeec239fe34"))
        binding.rvFeedWriteUploadImg.adapter!!.notifyItemInserted(0)
        binding.rvFeedWriteUploadImg.scrollToPosition(0)
    }

    private fun removeImg(position: Int) {
        imgTestList.removeAt(position)
        Timber.d("아이템: $imgTestList")

        binding.rvFeedWriteUploadImg.adapter!!.notifyItemRemoved(position)
        Timber.d("$position 번째 아이템")

    }
}