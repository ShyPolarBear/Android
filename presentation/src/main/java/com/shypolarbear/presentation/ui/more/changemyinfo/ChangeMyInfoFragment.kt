package com.shypolarbear.presentation.ui.more.changemyinfo

import android.net.Uri
import android.os.CountDownTimer
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentChangeMyInfoBinding
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADED
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADING
import com.shypolarbear.presentation.ui.join.NAME_RANGE
import com.shypolarbear.presentation.ui.join.pages.PHONE_NUMBER_DASH_INCLUDE
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.ImageUtil
import com.shypolarbear.presentation.util.InputState
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.emailPattern
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.phonePattern
import com.shypolarbear.presentation.util.setColorStateWithInput
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

const val NICKNAME_DUPLICATE_CHECK_TIME = 500

enum class availableState(val state: String) {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable")
}

@AndroidEntryPoint
class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()
    private val changeMyInfoArgs: ChangeMyInfoFragmentArgs by navArgs()
    private lateinit var phoneNumber: String
    private var nameState: InputState = InputState.OFF
    private var phoneNumberState: InputState = InputState.OFF
    private var emailState: InputState = InputState.OFF
    private val imageUtil = ImageUtil
    private lateinit var profileImageUri: Uri
    private var checkTimer: CountDownTimer? = null

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                profileImageUri = uri
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivChangeMyInfoProfile)
            }
        }

    private val inputDelayMillis = NICKNAME_DUPLICATE_CHECK_TIME

    override fun initView() {

        binding.apply {
            progressChangeMyInfoLoading.isVisible = true
            layoutChangeMyInfo.isVisible = false

            viewModel.getMyInfo()
            viewModel.myInfo.observe(viewLifecycleOwner) { info ->
                edtChangeMyInfoNickname.setText(info.nickName)
                edtChangeMyInfoPhoneNumber.setText(info.phoneNumber)
                edtChangeMyInfoEmail.setText(info.email)
                profileImageUri = info.profileImage.toUri()

                if (!info.profileImage.isNullOrBlank()) {
                    GlideUtil.loadCircleImage(requireContext(), info.profileImage.toUri(), binding.ivChangeMyInfoProfile)
                } else {
                    GlideUtil.loadCircleImage(requireContext(), url = null, view = binding.ivChangeMyInfoProfile, placeHolder = R.drawable.ic_user_base_profile)
                }

                progressChangeMyInfoLoading.isVisible = false
                layoutChangeMyInfo.isVisible = true
            }

            btnChangeMyInfoBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnChangeMyInfoImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            btnChangeMyInfoRevise.setOnClickListener {
                when {
                    nameState == InputState.ERROR || phoneNumberState == InputState.ERROR || emailState == InputState.ERROR -> {
                        Toast.makeText(requireContext(), getString(R.string.check_my_info_term), Toast.LENGTH_SHORT).show()
                    }

                    listOf(nameState, phoneNumberState, emailState).any { it ==InputState.OFF } ||
                    listOf(edtChangeMyInfoNickname.text.toString(),
                        edtChangeMyInfoPhoneNumber.text.toString(),
                        edtChangeMyInfoEmail.text.toString()).any { it.isNullOrBlank() } -> {
                        Toast.makeText(requireContext(), getString(R.string.check_my_info_input), Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        val imageFileList: File? = uploadImage()

                        viewModel.requestChangeMyInfo(
                            nickName = edtChangeMyInfoNickname.text.toString(),
                            phoneNumber = edtChangeMyInfoPhoneNumber.text.toString(),
                            email = edtChangeMyInfoEmail.text.toString(),
                            profileImageFile = imageFileList
                        )
                    }
                }

                viewModel.uploadState.observe(viewLifecycleOwner) {
                    when(viewModel.uploadState.value) {
                        UPLOADING -> { }
                        UPLOADED -> {
                            Toast.makeText(requireContext(), getString(R.string.check_my_info_success), Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                    }
                }
            }

            edtChangeMyInfoNickname.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        setColorStateWithInput(
                            InputState.ON,
                            tvChangeMyInfoNameRule,
                            ivChangeMyInfoNameCheck
                        )
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        setColorStateWithInput(
                            InputState.ON,
                            tvChangeMyInfoNameRule,
                            ivChangeMyInfoNameCheck
                        )
                        checkTimer?.cancel()
                        startTimer(changeMyInfoArgs.nickName)
                    }

                    override fun afterTextChanged(s: Editable?) {
                        nameState = when {
                            s.isNullOrEmpty() -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.signup_name_rule)
                                InputState.ON
                            }

                            s.length !in NAME_RANGE -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.singup_error_text)
                                InputState.ERROR
                            }

                            else -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.signup_confirm_text)
                                InputState.ACCEPT
                            }
                        }

                        setColorStateWithInput(
                            nameState,
                            tvChangeMyInfoNameRule,
                            ivChangeMyInfoNameCheck
                        )
                        checkButtonActive()
                    }
                })
            }

            edtChangeMyInfoPhoneNumber.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))
                afterTextChanged { s ->
                    phoneNumberState = when {
                        s.isNullOrEmpty() -> {
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_detail)
                            InputState.ON
                        }

                        s.length == PHONE_NUMBER_DASH_INCLUDE -> {
                            phoneNumber = s.replace(phonePattern, "")
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_confirm)
                            InputState.ACCEPT
                        }

                        else -> {
                            phoneNumber = ""
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_error)
                            InputState.ERROR
                        }
                    }

                    setColorStateWithInput(
                        phoneNumberState,
                        tvChangeMyInfoPhoneNumberRule,
                        ivChangeMyInfoPhoneNumberCheck
                    )
                    checkButtonActive()
                }
            }

            edtChangeMyInfoEmail.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                afterTextChanged {
                    val match: MatchResult? = emailPattern.find(it.toString())

                    emailState = when {
                        it.isNullOrEmpty() -> {
                            InputState.ON
                        }

                        match == null -> {
                            tvChangeMyInfoEmailRule.text = getString(R.string.signup_mail_hint_error)
                            InputState.ERROR
                        }

                        else -> {
                            tvChangeMyInfoEmailRule.text = getString(R.string.signup_mail_hint_confirm)
                            InputState.ACCEPT
                        }
                    }

                    setColorStateWithInput(
                        emailState,
                        tvChangeMyInfoEmailRule,
                        ivChangeMyInfoEmailCheck
                    )
                    checkButtonActive()
                }
            }
        }

        viewModel.nickNameState.observe(viewLifecycleOwner) {
            when(it) {
                availableState.AVAILABLE -> {
                    nameState = InputState.ACCEPT
                    binding.tvChangeMyInfoNameRule.text = getString(R.string.signup_confirm_text)
                }
                else -> {
                    nameState = InputState.ERROR
                    binding.tvChangeMyInfoNameRule.text = getString(R.string.singup_duplicate_nickname_text)
                }
            }
            binding.edtChangeMyInfoNickname.setColorStateWithInput(
                nameState,
                binding.tvChangeMyInfoNameRule,
                binding.ivChangeMyInfoNameCheck
            )
            checkButtonActive()
        }
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

    private fun uploadImage(): File? { return imageUtil.uriToOptimizeImageFile(requireContext(), profileImageUri) }

    private fun checkButtonActive() {
        binding.apply {
            if(nameState == InputState.ACCEPT && emailState == InputState.ACCEPT && phoneNumberState == InputState.ACCEPT) {
                btnChangeMyInfoRevise.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.background_solid_blue_01_radius_15
                )
                btnChangeMyInfoRevise.setTextColor(requireContext().getColor(R.color.White_01))
            }
            else {
                btnChangeMyInfoRevise.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.background_solid_gray_06_radius_15
                )
                btnChangeMyInfoRevise.setTextColor(requireContext().getColor(R.color.Gray_03))
            }
        }
    }

    private fun startTimer(oldNickName: String) {
        checkTimer = object : CountDownTimer(inputDelayMillis.toLong(), 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                val newNickName = binding.edtChangeMyInfoNickname.text.toString()

                if (oldNickName != newNickName && newNickName.length in NAME_RANGE) {
                    viewModel.requestCheckNickName(newNickName)
                }
            }
        }
        checkTimer?.start()
    }
}