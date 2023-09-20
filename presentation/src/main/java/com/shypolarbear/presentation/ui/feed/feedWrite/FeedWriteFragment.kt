package com.shypolarbear.presentation.ui.feed.feedWrite

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedWriteBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.FragmentTotalStatus
import com.shypolarbear.presentation.ui.feed.feedTotal.WriteChangeDivider
import com.shypolarbear.presentation.ui.feed.feedTotal.fragmentTotalStatus
import com.shypolarbear.presentation.util.ImageType
import com.shypolarbear.presentation.util.convertUriToFile
import com.shypolarbear.presentation.util.convertUriToPath
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

const val IMAGE_ADD_INDEX = 0
const val IMAGE_MAX_COUNT = 5
const val IMAGE_ADD_MAX = 6

@AndroidEntryPoint
class FeedWriteFragment: BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel > (
    R.layout.fragment_feed_write
) {

    override val viewModel: FeedWriteViewModel by viewModels()
    private val feedWriteArgs: FeedWriteFragmentArgs by navArgs()
    private val imageUriList: MutableList<Uri> = mutableListOf()

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
                        imageUriList.addAll(0, uris)
                        Timber.d("$imageUriList")
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

                        val imgUriList = feed.feedImages.map { it.toUri() }
                        viewModel.addImgList(imgUriList)
                    }
                }
            }

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            viewModel.liveImgList.observe(viewLifecycleOwner) {
                feedWriteImgAdapter.submitList(it.toList())
            }

            btnFeedWriteBack.setOnClickListener {
                fragmentTotalStatus = FragmentTotalStatus.WRITE_BACK_BTN_CLICK
                findNavController().popBackStack()

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
                        uploadPost()
                    }
                }
            }
        }
    }

    private fun uploadPost() {
        val imageFileList: List<File> = imageUriList.map { it ->
            it.convertUriToFile(requireContext())
        }

        viewModel.requestUploadImages(ImageType.FEED.type, imageFileList)       // 이미지 업로드

        viewModel.uploadImageList.observe(viewLifecycleOwner) {     // 이미지 업로드 되면 실행
            when(feedWriteArgs.divider) {
                WriteChangeDivider.WRITE -> {
                    viewModel.writePost(
                        title = binding.edtFeedWriteTitle.text.toString(),
                        content = binding.edtFeedWriteContent.text.toString(),
                        feedImages = viewModel.uploadImageList.value
                    )
                }
                WriteChangeDivider.CHANGE -> {
                    viewModel.changePost(
                        feedId = feedWriteArgs.feedId,
                        content = binding.edtFeedWriteContent.text.toString(),
                        feedImages = viewModel.uploadImageList.value,
                        title = binding.edtFeedWriteTitle.text.toString()
                    )
                }
            }

            fragmentTotalStatus = FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK
            findNavController().popBackStack()
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
        imageUriList.removeAt(position)
        Timber.d("$imageUriList")
        viewModel.removeImgList(position)
    }
}