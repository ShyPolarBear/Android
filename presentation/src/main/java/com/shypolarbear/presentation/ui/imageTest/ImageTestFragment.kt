package com.shypolarbear.presentation.ui.imageTest

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import com.shypolarbear.domain.model.image.ImageFiles
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentImageTestBinding
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.ImageType
import com.shypolarbear.presentation.util.convertUriToFile
import com.shypolarbear.presentation.util.convertUriToPath
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class ImageTestFragment :
    BaseFragment<FragmentImageTestBinding, ImageTestViewModel>(R.layout.fragment_image_test) {
    override val viewModel: ImageTestViewModel by viewModels()
    val mimeType = "image/gif"

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                Timber.tag("IMG").d("${uri.convertUriToFile(requireContext())}")
                Timber.tag("IMG TYPE").d("${(File(uri.convertUriToPath(requireContext())))}")

                viewModel.requestUpload(ImageType.FEED.type, listOf(File(uri.convertUriToPath(requireContext())),File(uri.convertUriToPath(requireContext())),File(uri.convertUriToPath(requireContext()))))
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivSignupNameProfile)
            }
        }
    // 절대경로 변환

    override fun initView() {
        binding.apply {
            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }

    }
}