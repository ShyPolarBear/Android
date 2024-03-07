package com.beeeam.more

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beeeam.base.BaseFragment
import com.beeeam.more.dialog.LogOutDialog
import com.beeeam.myinfo.R
import com.beeeam.myinfo.databinding.FragmentMoreBinding
import com.beeeam.util.GlideUtil
import com.beeeam.util.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding, MoreViewModel>(
    R.layout.fragment_more,
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
                    MoreFragmentDirections.actionNavigationMoreToChangeMyInfoFragment(nickName),
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
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(requireContext().getString(com.beeeam.designsystem.R.string.more_open_source_library_link)))
                startActivity(intent)
            }

            layoutMoreContact.setOnClickListener {
                val email = Intent(Intent.ACTION_SEND)
                val beomjunEmail = requireContext().getString(com.beeeam.designsystem.R.string.Beomjun_Email)

                email.data = Uri.parse(requireContext().getString(com.beeeam.designsystem.R.string.more_contact_email_data))
                email.type = requireContext().getString(com.beeeam.designsystem.R.string.more_contact_email_type)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(beomjunEmail))
                email.putExtra(Intent.EXTRA_TEXT, requireContext().getString(com.beeeam.designsystem.R.string.more_contact_email_content))
                startActivity(email)
            }

            viewModel.loginState.observe(viewLifecycleOwner) {
                when (viewModel.loginState.value) {
                    LoginState.LOGIN -> { }
                    LoginState.LOGOUT -> {
                        findNavController().navigate(R.id.action_navigation_more_to_loginFragment)
                    }
                    else -> {}
                }
            }
        }
    }
}
