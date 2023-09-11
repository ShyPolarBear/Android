package com.shypolarbear.presentation.ui.imageTest

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentImageTestBinding
import com.shypolarbear.presentation.util.GlideUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ImageTestFragment :
    BaseFragment<FragmentImageTestBinding, ImageTestViewModel>(R.layout.fragment_image_test) {
    override val viewModel: ImageTestViewModel by viewModels()

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                Timber.tag("IMG").d("${uri.toFile()}")
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivSignupNameProfile)
            }
        }

    override fun initView() {
        binding.apply {
            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }
}