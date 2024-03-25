package com.beeeam.feed.feedWrite

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.beeeam.base.BaseFragment
import com.beeeam.feed.R
import com.beeeam.feed.databinding.FragmentFeedWriteBinding
import com.beeeam.util.ActiveState
import com.beeeam.util.Const.CONTENT_RANGE
import com.beeeam.util.Const.IMAGE_ADD_INDEX
import com.beeeam.util.Const.IMAGE_ADD_MAX
import com.beeeam.util.Const.IMAGE_MAX_COUNT
import com.beeeam.util.Const.TITLE_RANGE
import com.beeeam.util.Const.UPLOADED
import com.beeeam.util.Const.UPLOADING
import com.beeeam.util.Const.fragmentTotalStatus
import com.beeeam.util.FragmentTotalStatus
import com.beeeam.util.ImageUtil
import com.beeeam.util.WriteChangeDivider
import com.beeeam.util.updateButtonState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class FeedWriteFragment : BaseFragment<FragmentFeedWriteBinding, FeedWriteViewModel>(
    R.layout.fragment_feed_write,
) {

    override val viewModel: FeedWriteViewModel by viewModels()
    private val feedWriteArgs: FeedWriteFragmentArgs by navArgs()
    private var originalImageUriList: MutableList<Uri> = mutableListOf()
    private var originalFeedImages: List<String> = listOf()
    private val imageUtil = ImageUtil
    private var titleState: ActiveState = ActiveState.NONACTIVE
    private var contentState: ActiveState = ActiveState.NONACTIVE

    private val feedWriteImgAdapter = FeedWriteImgAdapter(
        onRemoveImgClick = { position: Int -> removeImg(position) },
    )
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(IMAGE_MAX_COUNT)) { uris ->
            uris?.let {
                when (viewModel.selectedLiveImgList.value!!.size + uris.size) {
                    in IMAGE_ADD_MAX..Int.MAX_VALUE -> {
                        Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.feed_write_image_count_msg), Toast.LENGTH_SHORT).show()
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
            when (feedWriteArgs.divider) {
                WriteChangeDivider.WRITE -> {
                }
                WriteChangeDivider.CHANGE -> {
                    viewModel.loadFeedDetail(feedWriteArgs.feedId)
                    viewModel.feed.observe(viewLifecycleOwner) { feed ->
                        edtFeedWriteTitle.setText(feed.title)
                        edtFeedWriteContent.setText(feed.content)

                        originalImageUriList = feed.feedImages.map { it.toUri() }.toMutableList()
                        originalFeedImages = feed.feedImages
                        viewModel.addImgList(originalImageUriList)
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
                        Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.feed_write_title_msg), Toast.LENGTH_SHORT).show()
                    }
                    edtFeedWriteContent.text.toString().isBlank() -> {
                        Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.feed_write_content_msg), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        uploadPost()
                    }
                }
            }

            edtFeedWriteTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    when {
                        s.isNullOrEmpty() -> {
                            titleState = ActiveState.NONACTIVE
                        }
                        s.length in TITLE_RANGE -> {
                            titleState = ActiveState.ACTIVE
                        }
                    }
                    updateButtonState(
                        requireContext(),
                        btnFeedWriteConfirm,
                        titleState == ActiveState.ACTIVE && contentState == ActiveState.ACTIVE,
                    )
                }
            })

            edtFeedWriteContent.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    when {
                        s.isNullOrEmpty() -> {
                            contentState = ActiveState.NONACTIVE
                        }
                        s.length in CONTENT_RANGE -> {
                            contentState = ActiveState.ACTIVE
                            updateButtonState(
                                requireContext(),
                                btnFeedWriteConfirm,
                                titleState == ActiveState.ACTIVE && contentState == ActiveState.ACTIVE,
                            )
                        }
                    }
                    updateButtonState(
                        requireContext(),
                        btnFeedWriteConfirm,
                        titleState == ActiveState.ACTIVE && contentState == ActiveState.ACTIVE,
                    )
                }
            })
        }
    }

    override fun onBackPressed() {
        moveToBack(FragmentTotalStatus.WRITE_BACK_BTN_CLICK)
    }

    private fun uploadPost() {
        when (feedWriteArgs.divider) {
            WriteChangeDivider.WRITE -> {
                when {
                    // 이미지 없는 게시물
                    viewModel.selectedLiveImgList.value.isNullOrEmpty() -> {
                        viewModel.writeNoImagePost(
                            title = binding.edtFeedWriteTitle.text.toString(),
                            content = binding.edtFeedWriteContent.text.toString(),
                        )
                    }
                    // 이미지 있는 게시물
                    else -> {
                        val addedImageUriList = viewModel.selectedLiveImgList.value!!.map { it.toUri() } - originalImageUriList
                        val imageFileList: List<File> = uploadImage(addedImageUriList)

                        viewModel.writeImagePost(
                            imageFiles = imageFileList,
                            title = binding.edtFeedWriteTitle.text.toString(),
                            content = binding.edtFeedWriteContent.text.toString(),
                        )
                    }
                }
            }
            WriteChangeDivider.CHANGE -> {
                when {
                    // 선택된 이미지가 없는 경우
                    viewModel.selectedLiveImgList.value.isNullOrEmpty() -> {
                        Timber.d("선택된 이미지 없음")
                        viewModel.changePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            feedImages = viewModel.selectedLiveImgList.value,
                            title = binding.edtFeedWriteTitle.text.toString(),
                        )
                    }

                    // 이미지 리스트에 변화가 없는 경우
                    viewModel.selectedLiveImgList.value!!.map { it.toUri() } == originalImageUriList -> {
                        Timber.d("변화 없음")
                        viewModel.changePost(
                            feedId = feedWriteArgs.feedId,
                            content = binding.edtFeedWriteContent.text.toString(),
                            feedImages = originalFeedImages,
                            title = binding.edtFeedWriteTitle.text.toString(),
                        )
                    }

                    // 이미지 리스트에 변화가 생긴 경우
                    viewModel.selectedLiveImgList.value!!.map { it.toUri() } != originalImageUriList -> {
                        val addedImageUriList = viewModel.selectedLiveImgList.value!!.map { it.toUri() } - originalImageUriList // 새로 추가 된 거 (선택된 이미지 리스트 - 기존 이미지 리스트)
                        val deletedImagesUriList = originalImageUriList - viewModel.selectedLiveImgList.value!!.map { it.toUri() } // 삭제 된 거 (기존 이미지 리스트- 선택된 이미지 리스트)
                        val remainImageUriList = originalImageUriList - deletedImagesUriList // 남아 있는 거 (기존 이미지 리스트- 삭제된 이미지 리스트)

                        when {
                            addedImageUriList.isNullOrEmpty() -> {
                                viewModel.changePost(
                                    feedId = feedWriteArgs.feedId,
                                    content = binding.edtFeedWriteContent.text.toString(),
                                    feedImages = remainImageUriList.map { it.toString() },
                                    title = binding.edtFeedWriteTitle.text.toString(),
                                )
                            }
                            else -> {
                                viewModel.changeImagePost(
                                    feedId = feedWriteArgs.feedId,
                                    content = binding.edtFeedWriteContent.text.toString(),
                                    originalImages = remainImageUriList.map { it.toString() },
                                    addedImageFiles = uploadImage(addedImageUriList)!!,
                                    title = binding.edtFeedWriteTitle.text.toString(),
                                )
                            }
                        }
                    }
                }
            }
        }

        // 이미지, 게시물 모두 업로드 된 경우
        viewModel.uploadState.observe(viewLifecycleOwner) {
            when (viewModel.uploadState.value) {
                UPLOADING -> { }
                UPLOADED -> { moveToBack(FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK) }
            }
        }
    }

    private fun addImg() {
        when (viewModel.selectedLiveImgList.value!!.size) {
            in IMAGE_MAX_COUNT..Int.MAX_VALUE -> {
                Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.feed_write_image_count_msg), Toast.LENGTH_SHORT).show()
            }
            else -> {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private fun removeImg(position: Int) { viewModel.removeImgList(position) }

    private fun moveToBack(moveState: FragmentTotalStatus) {
        fragmentTotalStatus = moveState
        findNavController().popBackStack()
    }

    private fun uploadImage(addedImageUriList: List<Uri>): List<File> {
        return addedImageUriList.map { imageUtil.uriToOptimizeImageFile(requireContext(), it)!! }
    }
}
