package com.shypolarbear.presentation.ui.imageTest

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentImageTestBinding
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.ImageType
import com.shypolarbear.presentation.util.convertUriToFile
import com.shypolarbear.presentation.util.convertUriToPath
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class ImageTestFragment :
    BaseFragment<FragmentImageTestBinding, ImageTestViewModel>(R.layout.fragment_image_test) {
    override val viewModel: ImageTestViewModel by viewModels()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                Timber.tag("IMG").d("${uri.convertUriToFile(requireContext())}")
                Timber.tag("IMG TYPE").d("${(File(uri.convertUriToPath(requireContext())))}")
                viewModel.requestUpload(ImageType.PROFILE.type, listOf(File(uri.convertUriToPath(requireContext()))))
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivSignupNameProfile)
            }
        }

    override fun initView() {
        binding.apply {
            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            ivDel.setOnClickListener {
                viewModel.requestDelete(listOf("https://shypolarbear-s3-bucket.s3.ap-northeast-2.amazonaws.com/profile/1000000018_e6d395a9-4730-456a-a21d-0dc6df8c4912.jpg"))
            }
        }

    }
}