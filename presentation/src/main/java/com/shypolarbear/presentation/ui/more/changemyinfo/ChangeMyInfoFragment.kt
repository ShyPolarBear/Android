package com.shypolarbear.presentation.ui.more.changemyinfo

import android.net.Uri
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentChangeMyInfoBinding
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADED
import com.shypolarbear.presentation.ui.feed.feedWrite.UPLOADING
import com.shypolarbear.presentation.ui.join.NAME_RANGE
import com.shypolarbear.presentation.ui.join.pages.PHONE_NUMBER_DASH_INCLUDE
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.InputState
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.convertUriToFile
import com.shypolarbear.presentation.util.emailPattern
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.phonePattern
import com.shypolarbear.presentation.util.setColorStateWithInput
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()
    private lateinit var phoneNumber: String
    private var nameState: InputState = InputState.OFF
    private var phoneNumberState: InputState = InputState.OFF
    private var emailState: InputState = InputState.OFF
    private lateinit var profileImageUri: Uri

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                profileImageUri = uri
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivChangeMyInfoProfile)
            }
        }

    override fun initView() {

        binding.apply {
            viewModel.getMyInfo()
            viewModel.myInfo.observe(viewLifecycleOwner) { info ->
                edtChangeMyInfoNickname.setText(info.nickName)
                edtChangeMyInfoPhoneNumber.setText(info.phoneNumber)
                edtChangeMyInfoEmail.setText(info.email)
                profileImageUri = info.profileImage.toUri()

                if (!info.profileImage.isNullOrBlank()) {
                    GlideUtil.loadImage(requireContext(), info.profileImage, binding.ivChangeMyInfoProfile)
                } else {
                    GlideUtil.loadImage(requireContext(), url = null, view = binding.ivChangeMyInfoProfile, placeHolder = R.drawable.ic_user_base_profile)
                }
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
                        val imageFileList: File = profileImageUri.convertUriToFile(requireContext())
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
                }
            }
        }
    }
}