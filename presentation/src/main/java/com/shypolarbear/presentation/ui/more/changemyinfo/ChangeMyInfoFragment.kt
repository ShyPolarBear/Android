package com.shypolarbear.presentation.ui.more.changemyinfo

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentChangeMyInfoBinding

class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()

    override fun initView() {

    }
}