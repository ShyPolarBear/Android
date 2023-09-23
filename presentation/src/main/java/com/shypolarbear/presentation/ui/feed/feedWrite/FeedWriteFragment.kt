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
    private val addedImageUriList: MutableList<Uri> = mutableListOf()
    private var originalImageUriList: MutableList<Uri> = mutableListOf()
    private var originalFeedImages: List<String> = listOf()

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
                        addedImageUriList.addAll(0, uris)
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
                        originalImageUriList = feed.feedImages.map { it.toUri() }.toMutableList()
                        originalFeedImages = feed.feedImages
                        viewModel.addImgList(imageUriList)
                        viewModel.setUploadImageList(feed.feedImages)
                    }
                }
            }

            rvFeedWriteUploadImg.adapter = feedWriteImgAdapter
            viewModel.selectedLiveImgList.observe(viewLifecycleOwner) {
                feedWriteImgAdapter.submitList(it.toList())
            }

            btnFeedWriteBack.setOnClickListener { moveToBack(FragmentTotalStatus.WRITE_BACK_BTN_CLICK) }

            btnFeedWriteAddPhoto.setOnClickListener { addImg() }

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
                        val imageFileList: List<File> = addedImageUriList.map { it.convertUriToFile(requireContext()) }

                        viewModel.writeImagePost(
                            imageFiles = imageFileList,
                            title = binding.edtFeedWriteTitle.text.toString(),
                            content = binding.edtFeedWriteContent.text.toString()
                        )
                    }
                }
            }
            WriteChangeDivider.CHANGE -> {
                when {
                    // 이미지 추가 안된 경우 (이미지 제거 OR 제목이나 내용 수정)
                    (originalImageUriList == imageUriList) and addedImageUriList.isNullOrEmpty() -> {
                        viewModel.changePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            feedImages = originalFeedImages,
                            title = binding.edtFeedWriteTitle.text.toString()
                        )
                    }
                    // 이미지 삭제만 된 경우
                    (originalImageUriList.size > imageUriList.size) -> {
                        viewModel.changePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            feedImages = viewModel.uploadImageList.value,
                            title = binding.edtFeedWriteTitle.text.toString()
                        )
                    }
                    // 이미지 추가만 된 경우
                    (originalImageUriList.size < imageUriList.size) -> {
                        val imageFileList: List<File> = addedImageUriList.map { it.convertUriToFile(requireContext()) }
                        viewModel.changeImagePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            originalImages = originalFeedImages,
                            addedImageFiles = imageFileList,
                            title = binding.edtFeedWriteTitle.text.toString()
                        )
                    }
                    // 이미지 리스트에 수정이 있지만 기존 리스트와 개수가 같은 경우
                    (originalImageUriList.size == imageUriList.size) and (originalImageUriList != imageUriList) -> {
                        val imageFileList: List<File> = addedImageUriList.map { it.convertUriToFile(requireContext()) }
                        viewModel.changeImagePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            originalImages = imageUriList.map { it.toString() },
                            addedImageFiles = imageFileList,
                            title = binding.edtFeedWriteTitle.text.toString()
                        )
                    }
                    // 이미지가 없는 게시물에서 이미지를 추가하는 경우
                    (originalImageUriList.size == 0) and (addedImageUriList.size > 0) -> {
                        val imageFileList: List<File> = addedImageUriList.map { it.convertUriToFile(requireContext()) }

                        viewModel.changeImagePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            originalImages = listOf(),
                            addedImageFiles = imageFileList,
                            title = binding.edtFeedWriteTitle.text.toString()
                        )
                    }
                }
            }
        }

        // 이미지, 게시물 모두 업로드 된 경우
        viewModel.uploadState.observe(viewLifecycleOwner) {
            when(viewModel.uploadState.value) {
                UPLOADING -> { }
                UPLOADED -> { moveToBack(FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK) }
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
        imageUriList.removeAt(position)
        viewModel.removeImgList(position)
    }

    private fun moveToBack(moveState: FragmentTotalStatus) {
        fragmentTotalStatus = moveState
        findNavController().popBackStack()
    }
}