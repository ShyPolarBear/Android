package com.beeeam.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeeam.ranking.databinding.ItemRankingBinding
import com.beeeam.ui.LoadingViewHolder
import com.beeeam.ui.databinding.ItemFeedLoadingBinding
import com.beeeam.util.Const.UNRANKED
import com.beeeam.util.GlideUtil
import com.beeeam.util.MyFeedType
import com.shypolarbear.domain.model.ranking.Ranking

class RankingAdapter :
    ListAdapter<Ranking, RecyclerView.ViewHolder>(RankingDiffCallback()) {

    inner class ItemRankingViewHolder(private val binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(totalRanking: Ranking) {
            binding.apply {
                tvRankingRank.text = if (totalRanking.rank == UNRANKED) {
                    binding.root.context.getString(com.beeeam.designsystem.R.string.ranking_null)
                } else {
                    totalRanking.rank.toString()
                }
                GlideUtil.loadCircleImage(
                    binding.root.context,
                    totalRanking.profileImage,
                    ivRankingProfile,
                    com.beeeam.designsystem.R.drawable.ic_user_base_profile,
                )
                tvRankingName.text = totalRanking.nickName
                tvRankingPoint.text =
                    binding.root.context.getString(com.beeeam.designsystem.R.string.ranking_point_value, totalRanking.point)
                tvRankingPossible.text = binding.root.context.getString(
                    com.beeeam.designsystem.R.string.ranking_possible_value,
                    totalRanking.winningPercent,
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            ItemRankingViewHolder(
                ItemRankingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        } else {
            LoadingViewHolder(
                ItemFeedLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemRankingViewHolder) {
            holder.bindItems(currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class RankingDiffCallback : DiffUtil.ItemCallback<Ranking>() {

    override fun areItemsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem.rankingId == newItem.rankingId
    }

    override fun areContentsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem == newItem
    }
}
