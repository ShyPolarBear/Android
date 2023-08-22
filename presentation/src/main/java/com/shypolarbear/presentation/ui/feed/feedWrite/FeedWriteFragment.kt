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

@AndroidEntryPoint
class FeedWriteFragment: BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel > (
    R.layout.fragment_feed_write
) {

    override val viewModel: FeedWriteViewModel by viewModels()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            uris?.let {
                viewModel.addImgList(uris)
                binding.rvFeedWriteUploadImg.adapter!!.notifyItemRangeChanged(0, uris.size)
                binding.rvFeedWriteUploadImg.scrollToPosition(0)
            }
        }

    private val feedWriteImgAdapter = FeedWriteImgAdapter(
        onRemoveImgClick = { position: Int -> removeImg(position) }
    )

    override fun initView() {
        binding.apply {

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            viewModel.testImgList.observe(viewLifecycleOwner) {
                feedWriteImgAdapter.submitList(it)
            }

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
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun removeImg(position: Int) {
        viewModel.removeImgList(position)
        binding.rvFeedWriteUploadImg.adapter!!.notifyItemRemoved(position)
        Timber.d("${position + 1} 번째 아이템")

    }
}