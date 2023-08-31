package com.shypolarbear.presentation.ui.feed.feedWrite

import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shypolarbear.domain.model.feed.FeedWriteImg
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedWriteBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.WriteChangeDivider
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
    private val feedWriteArgs: FeedWriteFragmentArgs by navArgs()

    private val feedWriteImgAdapter = FeedWriteImgAdapter(
        onRemoveImgClick = { position: Int -> removeImg(position) }
    )
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(IMAGE_MAX_COUNT)) { uris ->
            uris?.let {
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

            when(feedWriteArgs.divider) {
                WriteChangeDivider.WRITE -> {

                }
                WriteChangeDivider.CHANGE -> {
                    viewModel.loadFeedDetail(feedWriteArgs.feedId)
                    viewModel.feed.observe(viewLifecycleOwner) { feed ->
                        edtFeedWriteTitle.setText(feed.title)
                        edtFeedWriteContent.setText(feed.content)

                        val imgUriList = feed.feedImage.map { it.toUri() }
                        viewModel.addImgList(imgUriList)
                    }
                }
            }

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            viewModel.liveImgList.observe(viewLifecycleOwner) {
                feedWriteImgAdapter.submitList(it.toList())
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
                        when(feedWriteArgs.divider) {
                            WriteChangeDivider.WRITE -> {

                            }
                            WriteChangeDivider.CHANGE -> {
                                viewModel.changePost(
                                    feedId = feedWriteArgs.feedId,
                                    content = edtFeedWriteContent.text.toString(),
                                    feedImages = null,
                                    title = edtFeedWriteTitle.text.toString()
                                )
                            }
                        }
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
        viewModel.removeImgList(position)
    }
}