package com.shypolarbear.presentation.ui.quiz.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.presentation.databinding.ItemQuizMainRecentFeedBinding

class QuizMainAdapter(
    private val _items: List<String>,
) : RecyclerView.Adapter<QuizMainAdapter.QuizMainViewHolder>() {
    private lateinit var binding: ItemQuizMainRecentFeedBinding

    companion object{
        fun initAdapter(items: List<String>) =
            QuizMainAdapter(items)
    }

    inner class QuizMainViewHolder(private val binding: ItemQuizMainRecentFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
            }
        }

        fun bind(item: String){
            binding.apply {
                itemQuizMainTv.text = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizMainViewHolder {
        binding = ItemQuizMainRecentFeedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuizMainViewHolder(binding)
    }

    override fun getItemCount() = _items.size

    override fun onBindViewHolder(holder: QuizMainViewHolder, position: Int) {
        holder.bind(_items[position])
    }
}