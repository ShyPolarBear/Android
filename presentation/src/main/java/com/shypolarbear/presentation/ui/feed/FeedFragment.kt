package com.shypolarbear.presentation.ui.feed

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedBinding
import com.skydoves.powermenu.kotlin.powerMenu

class FeedFragment: BaseFragment<FragmentFeedBinding, FeedViewModel> (
    R.layout.fragment_feed
){
    override val viewModel: FeedViewModel by viewModels()

    private val moreMenu by powerMenu<FeedMenuFactory>()

    override fun initView() {
        moreMenu?.showAsDropDown(binding.feedToolbarSort)
        binding.feedToolbarSort.setOnClickListener {
            moreMenu?.showAsDropDown(it)
        }

    }
}