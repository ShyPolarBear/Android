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
    private var imageUriList: MutableList<Uri> = mutableListOf()

    private val feedWriteImgAdapter = FeedWriteImgAdapter(
        onRemoveImgClick = { position: Int -> removeImg(position) }
    )
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(IMAGE_MAX_COUNT)) { uris ->
            uris?.let {
                when(viewModel.selectedLiveImgList.value!!.size + uris.size) {
                    in IMAGE_ADD_MAX..Int.MAX_VALUE -> {
                        Toast.makeText(requireContext(), getString(R.string.feed_write_image_count_msg), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        imageUriList.addAll(0, uris)
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

                        imageUriList = feed.feedImages.map { it.toUri() }.toMutableList()
                        viewModel.addImgList(imageUriList)
                        viewModel.setUploadImageList(feed.feedImages)
                    }
                }
            }

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            viewModel.selectedLiveImgList.observe(viewLifecycleOwner) {
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
        when(feedWriteArgs.divider) {
            WriteChangeDivider.WRITE -> {
                when {
                    // 이미지 없는 게시물
                    imageUriList.isEmpty() -> {
                        viewModel.writeNoImagePost(
                            title = binding.edtFeedWriteTitle.text.toString(),
                            content = binding.edtFeedWriteContent.text.toString()
                        )
                    }
                    // 이미지 있는 게시물
                    else -> {
                        val imageFileList: List<File> = imageUriList.map { it.convertUriToFile(requireContext()) }

                        viewModel.writeImagePost(
                            imageType = ImageType.FEED.type,
                            imageFiles = imageFileList,
                            title = binding.edtFeedWriteTitle.text.toString(),
                            content = binding.edtFeedWriteContent.text.toString()
                        )
                    }
                }
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

        // 이미지, 게시물 모두 업로드 된 경우
        viewModel.uploadState.observe(viewLifecycleOwner) {
            Timber.d("viewModel.uploadState: ${viewModel.uploadState}")
            when(viewModel.uploadState.value) {
                UPLOADING -> {

                }
                UPLOADED -> {
                    Timber.d("viewModel.uploadState: ${viewModel.uploadState}")
                    fragmentTotalStatus = FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun addImg() {
        when(viewModel.selectedLiveImgList.value!!.size) {
            in IMAGE_MAX_COUNT..Int.MAX_VALUE -> {
                Toast.makeText(requireContext(), getString(R.string.feed_write_image_count_msg), Toast.LENGTH_SHORT).show()
            }
            else -> {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun removeImg(position: Int) {
        Timber.d("imageUriList: $imageUriList")
        imageUriList.removeAt(position)
        Timber.d("imageUriList: $imageUriList")
        viewModel.removeImgList(position)
    }
}