package com.shypolarbear.presentation.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.domain.model.ranking.Ranking
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.ItemFeedLoadingBinding
import com.shypolarbear.presentation.databinding.ItemRankingBinding
import com.shypolarbear.presentation.ui.mypage.adapter.LoadingViewHolder
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.MyFeedType

class RankingAdapter(private val _items: List<Ranking>) :
    ListAdapter<Ranking, RecyclerView.ViewHolder>(RankingDiffCallback()) {

    inner class ItemRankingViewHolder(private val binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(myRanking: Ranking) {
            binding.apply {
                tvRankingRank.text = myRanking.rank.toString()
                GlideUtil.loadImage(binding.root.context, myRanking.profileImage, ivRankingProfile)
                tvRankingName.text = myRanking.nickName
                tvRankingPoint.text = binding.root.context.getString(R.string.ranking_point_value, myRanking.point)

                tvRankingPossible.text = binding.root.context.getString(R.string.ranking_possible_value, myRanking.winningPercent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_items[position] != null) MyFeedType.ITEM.state else MyFeedType.LOADING.state
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MyFeedType.ITEM.state) {
            ItemRankingViewHolder(
                ItemRankingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            LoadingViewHolder(
                ItemFeedLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemRankingViewHolder) {
            holder.bindItems(_items[position])
        }
    }

    override fun getItemCount(): Int {
        return _items.size
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