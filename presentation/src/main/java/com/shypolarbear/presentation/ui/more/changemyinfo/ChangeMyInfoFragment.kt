package com.shypolarbear.presentation.ui.more.changemyinfo

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentChangeMyInfoBinding

class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()

    override fun initView() {
        val bottomNavigationViewMainActivity = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationViewMainActivity.isVisible = false

        binding.apply {
            btnChangeMyInfoBack.setOnClickListener {
                findNavController().navigate(R.id.action_changeMyInfoFragment_to_navigation_more)
            }
        }
    }
}