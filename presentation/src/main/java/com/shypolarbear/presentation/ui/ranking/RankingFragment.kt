package com.shypolarbear.presentation.ui.ranking

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentRankingBinding
import com.shypolarbear.presentation.ui.mypage.FeedContentType
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.infiniteScroll
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment :
    BaseFragment<FragmentRankingBinding, RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    override fun initView() {
        viewModel.loadRankingData()

        viewModel.myRankingResponse.observe(viewLifecycleOwner) { myRanking ->
            myRanking?.let {
                binding.apply {
                    tvRankingRank.text = myRanking.rank.toString()
                    GlideUtil.loadImage(requireContext(), myRanking.profileImage, ivRankingProfile)
                    tvRankingName.text = myRanking.nickName
                    tvRankingPoint.text = getString(R.string.ranking_point_value, myRanking.point)
                    tvRankingPossible.text =
                        getString(R.string.ranking_possible_value, myRanking.winningPercent)
                }
            }
        }

        viewModel.totalRankingResponse.observe(viewLifecycleOwner) { totalRanking ->
            totalRanking?.let {
                binding.apply {
                    rankingProgressbar.isVisible = false
                    setAdapter(RankingAdapter(totalRanking.content))
                }
            }
        }

        binding.apply {
            rankingProgressbar.isVisible = true
        }
    }

    private fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        binding.rvRanking.adapter = adapter
        binding.rvRanking.infiniteScroll {
            viewModel.loadMoreRanking()
        }
    }
}