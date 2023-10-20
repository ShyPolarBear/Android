package com.shypolarbear.presentation.ui.noti

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentNotiBinding
import com.shypolarbear.presentation.ui.ranking.RankingAdapter
import com.shypolarbear.presentation.util.infiniteScroll
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiFragment : BaseFragment<FragmentNotiBinding, NotiViewModel>(R.layout.fragment_noti) {
    override val viewModel: NotiViewModel by viewModels()

    override fun initView() {
        val notiAdapter = NotiAdapter()
        viewModel.notificationList.observe(viewLifecycleOwner) { notifications ->
            notifications?.let {
                notiAdapter.submitList(viewModel.notificationList.value)
                binding.notiProgressbar.isVisible = false
            }
        }

        binding.apply {
            rvNoti.adapter = notiAdapter
            notiProgressbar.isVisible = true
            notiBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}