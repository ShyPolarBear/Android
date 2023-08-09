package com.shypolarbear.presentation.ui.more

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMoreBinding

class MoreFragment: BaseFragment<FragmentMoreBinding, MoreViewModel> (
    R.layout.fragment_more
) {

    override val viewModel: MoreViewModel by viewModels()

    override fun initView() {

    }
}