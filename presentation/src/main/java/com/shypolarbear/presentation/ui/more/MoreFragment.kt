package com.shypolarbear.presentation.ui.more

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMoreBinding
import com.shypolarbear.presentation.ui.more.dialog.LogOutDialog
import com.shypolarbear.presentation.util.GlideUtil
import dagger.hilt.android.AndroidEntryPoint

enum class LoginState(val state: String) {
    LOGIN("Login"),
    LOGOUT("Logout")
}

@AndroidEntryPoint
class MoreFragment: BaseFragment<FragmentMoreBinding, MoreViewModel> (
    R.layout.fragment_more
) {

    override val viewModel: MoreViewModel by viewModels()
    private lateinit var logoutDialog: LogOutDialog

    override fun initView() {
        var nickName = ""
        logoutDialog = LogOutDialog(requireContext())

        binding.apply {
            viewModel.getMyInfo()
            viewModel.myInfo.observe(viewLifecycleOwner) { info ->
                nickName = info.nickName

                tvMoreUserNickname.setText(nickName)

                if (!info.profileImage.isNullOrBlank()) {
                    GlideUtil.loadImage(requireContext(), info.profileImage, binding.ivMoreUserProfile)
                } else {
                    GlideUtil.loadImage(requireContext(), url = null, view = binding.ivMoreUserProfile, placeHolder = R.drawable.ic_user_base_profile)
                }
            }

            layoutMoreMyInfoChange.setOnClickListener {
                findNavController().navigate(
                    MoreFragmentDirections.actionNavigationMoreToChangeMyInfoFragment(nickName)
                )
            }

            layoutMoreMyPostAndComment.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_more_to_myPageFragment)
            }

            layoutMoreLogOut.setOnClickListener {
                logoutDialog.showDialog()
                logoutDialog.alertDialog.setOnCancelListener {
                    viewModel.requestLogout()

                }
            }

            layoutMoreOpenSourceLibrary.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(requireContext().getString(R.string.more_open_source_library_link)))
                startActivity(intent)
            }

            layoutMoreContact.setOnClickListener {
                val email = Intent(Intent.ACTION_SEND)
                val beomjunEmail = requireContext().getString(R.string.Beomjun_Email)

                email.data = Uri.parse(requireContext().getString(R.string.more_contact_email_data))
                email.type = requireContext().getString(R.string.more_contact_email_type)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(beomjunEmail))
                email.putExtra(Intent.EXTRA_TEXT, requireContext().getString(R.string.more_contact_email_content))
                startActivity(email)
            }

            viewModel.loginState.observe(viewLifecycleOwner) {
                when(viewModel.loginState.value) {
                    LoginState.LOGIN -> {  }
                    LoginState.LOGOUT -> {
                        findNavController().navigate(R.id.action_navigation_more_to_loginFragment)
                    }
                    else -> {}
                }
            }
        }
    }
}