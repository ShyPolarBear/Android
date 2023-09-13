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
                viewModel.requestModify(ImageType.PROFILE.type, listOf(File(uri.convertUriToPath(requireContext()))), listOf("https://shypolarbear-s3-bucket.s3.ap-northeast-2.amazonaws.com/feed/1000000018_a0cd8025-ea75-441e-9cc9-cc247c269e7f.jpg"))
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivSignupNameProfile)
            }
        }

    override fun initView() {
        binding.apply {
            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

//            ivDel.setOnClickListener {
//                viewModel.requestDelete(listOf("https://shypolarbear-s3-bucket.s3.ap-northeast-2.amazonaws.com/feed/1000000018_a0cd8025-ea75-441e-9cc9-cc247c269e7f.jpg"))
//            }

            ivDel.setOnClickListener {
                //new Image
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            }
        }

    }
}