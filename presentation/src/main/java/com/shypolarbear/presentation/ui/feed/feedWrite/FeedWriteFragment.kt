package com.shypolarbear.presentation.ui.feed.feedWrite

import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedWriteBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

const val IMAGE_ADD_INDEX = 0
const val IMAGE_MAX_COUNT = 5
const val IMAGE_ADD_MAX = 6

@AndroidEntryPoint
class FeedWriteFragment: BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel > (
    R.layout.fragment_feed_write
) {

    override val viewModel: FeedWriteViewModel by viewModels()
    private val feedWriteImgAdapter = FeedWriteImgAdapter(
        onRemoveImgClick = { position: Int -> removeImg(position) }
    )
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(IMAGE_MAX_COUNT)) { uris ->
            uris?.let {
                Timber.d("${viewModel.liveImgList.value!!.size + uris.size}")
                when(viewModel.liveImgList.value!!.size + uris.size) {
                    in IMAGE_ADD_MAX..Int.MAX_VALUE -> {
                        Toast.makeText(requireContext(), getString(R.string.feed_write_image_count_msg), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        viewModel.addImgList(uris)
                        binding.rvFeedWriteUploadImg.scrollToPosition(IMAGE_ADD_INDEX)
                    }
                }

            }
        }

    override fun initView() {
        binding.apply {

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            viewModel.liveImgList.observe(viewLifecycleOwner) {
                Timber.d("3")
                feedWriteImgAdapter.submitList(it)
                Timber.d("이미지 리스트 변경 감지, it: $it")
            }

            btnFeedWriteBack.setOnClickListener {
                findNavController().navigate(R.id.action_feedWriteFragment_to_navigation_feed)
            }

            btnFeedWriteAddPhoto.setOnClickListener {
                addImg()
            }

            btnFeedWriteConfirm.setOnClickListener {
                when {
                    edtFeedWriteTitle.text.toString().isBlank() -> {
                        Toast.makeText(requireContext(), getString(R.string.feed_write_title_msg), Toast.LENGTH_SHORT).show()
                    }
                    edtFeedWriteContent.text.toString().isBlank() -> {
                        Toast.makeText(requireContext(), getString(R.string.feed_write_content_msg), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        findNavController().navigate(R.id.action_feedWriteFragment_to_navigation_feed)
                    }
                }
            }
        }
    }

    private fun addImg() {
        when(viewModel.liveImgList.value!!.size) {
            in IMAGE_MAX_COUNT..Int.MAX_VALUE -> {
                Toast.makeText(requireContext(), getString(R.string.feed_write_image_count_msg), Toast.LENGTH_SHORT).show()
            }
            else -> {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun removeImg(position: Int) {
        Timber.d("2")
        viewModel.removeImgList(position)

//        binding.rvFeedWriteUploadImg.adapter!!.notifyItemRemoved(position)
    }
}