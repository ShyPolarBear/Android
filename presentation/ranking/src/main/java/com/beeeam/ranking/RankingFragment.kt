package com.beeeam.ranking

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.beeeam.base.BaseFragment
import com.beeeam.ranking.databinding.FragmentRankingBinding
import com.beeeam.util.Const.UNRANKED
import com.beeeam.util.GlideUtil
import com.beeeam.util.infiniteScroll
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment :
    BaseFragment<FragmentRankingBinding, RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    override fun initView() {
        val rankingAdapter = RankingAdapter()
        val sampleProductUrl = "https://media.bunjang.co.kr/product/234579740_1_1693213317_w360.jpg"
        viewModel.loadRankingData()
        viewModel.myRankingResponse.observe(viewLifecycleOwner) { myRanking ->
            myRanking?.let {
                binding.apply {
                    tvRankingRank.text = if (myRanking.rank == UNRANKED) {
                        getString(com.beeeam.designsystem.R.string.ranking_null)
                    } else {
                        myRanking.rank.toString()
                    }
                    GlideUtil.loadCircleImage(
                        requireContext(),
                        myRanking.profileImage,
                        ivRankingProfile,
                        com.beeeam.designsystem.R.drawable.ic_user_base_profile,
                    )
                    tvRankingName.text = myRanking.nickName
                    tvRankingPoint.text = getString(com.beeeam.designsystem.R.string.ranking_point_value, myRanking.point)
                    tvRankingPossible.text =
                        getString(com.beeeam.designsystem.R.string.ranking_possible_value, myRanking.winningPercent)
                }
            }
        }

        viewModel.totalRankingResponse.observe(viewLifecycleOwner) { totalRanking ->
            totalRanking?.let {
                binding.apply {
                    rankingAdapter.submitList(totalRanking.content)
                    rankingProgressbar.isVisible = false
                }
            }
        }

        binding.apply {
            setAdapter(rankingAdapter)
            rankingProgressbar.isVisible = true
            GlideUtil.loadImage(requireContext(), sampleProductUrl, ivRankingSampleProduct)
        }
    }

    private fun setAdapter(adapter: RankingAdapter) {
        binding.rvRanking.adapter = adapter
        binding.rvRanking.infiniteScroll {
            viewModel.loadMoreRanking()
        }
    }
}
