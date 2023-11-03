package com.shypolarbear.presentation.ui.more

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
        logoutDialog = LogOutDialog(requireContext())

        binding.apply {
            viewModel.getMyInfo()
            viewModel.myInfo.observe(viewLifecycleOwner) { info ->
                tvMoreUserNickname.setText(info.nickName)
                if (!info.profileImage.isNullOrBlank()) {
                    GlideUtil.loadImage(requireContext(), info.profileImage, binding.ivMoreUserProfile)
                } else {
                    GlideUtil.loadImage(requireContext(), url = null, view = binding.ivMoreUserProfile, placeHolder = R.drawable.ic_user_base_profile)
                }
            }

            layoutMoreMyInfoChange.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_more_to_changeMyInfoFragment)
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