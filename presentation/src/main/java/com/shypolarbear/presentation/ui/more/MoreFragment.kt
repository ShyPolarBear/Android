package com.shypolarbear.presentation.ui.more

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment: BaseFragment<FragmentMoreBinding, MoreViewModel> (
    R.layout.fragment_more
) {

    override val viewModel: MoreViewModel by viewModels()

    override fun initView() {

        binding.apply {

            layoutMoreMyInfoChange.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_more_to_changeMyInfoFragment)
            }
        }
    }
}