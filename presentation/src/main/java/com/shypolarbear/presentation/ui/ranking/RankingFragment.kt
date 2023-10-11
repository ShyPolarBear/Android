package com.shypolarbear.presentation.ui.ranking

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.shypolarbear.domain.model.ranking.Ranking
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentRankingBinding
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.infiniteScroll
import dagger.hilt.android.AndroidEntryPoint


const val UNRANKED = -1

@AndroidEntryPoint
class RankingFragment :
    BaseFragment<FragmentRankingBinding, RankingViewModel>(R.layout.fragment_ranking) {
    override val viewModel: RankingViewModel by viewModels()
    override fun initView() {
        val sampleProductUrl = "https://media.bunjang.co.kr/product/234579740_1_1693213317_w360.jpg"
        viewModel.loadRankingData()
        viewModel.myRankingResponse.observe(viewLifecycleOwner) { myRanking ->
            myRanking?.let {
                binding.apply {
                    tvRankingRank.text = if (myRanking.rank == UNRANKED) {
                        getString(R.string.ranking_null)
                    } else {
                        myRanking.rank.toString()
                    }
                    GlideUtil.loadCircleImage(
                        requireContext(),
                        myRanking.profileImage,
                        ivRankingProfile,
                        R.drawable.ic_user_base_profile
                    )
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
                    setAdapter(
                        RankingAdapter(), totalRanking.content
                    )
                    rankingProgressbar.isVisible = false
                }
            }
        }

        binding.apply {
            rankingProgressbar.isVisible = true
            GlideUtil.loadImage(requireContext(), sampleProductUrl, ivRankingSampleProduct)
        }
    }

    private fun setAdapter(adapter: RankingAdapter, contentList: List<Ranking>) {
        binding.rvRanking.adapter = adapter
        adapter.submitList(contentList)
        binding.rvRanking.infiniteScroll {
            viewModel.loadMoreRanking()
        }
    }
}