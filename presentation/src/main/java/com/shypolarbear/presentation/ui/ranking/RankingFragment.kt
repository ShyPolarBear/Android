package com.shypolarbear.presentation.ui.ranking

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentRankingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment :
    BaseFragment<FragmentRankingBinding, RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    override fun initView() {

    }
}