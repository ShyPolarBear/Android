package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding

class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizDailyViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizDailyViewModel by viewModels()

    override fun initView() {
        binding.apply {

        }
    }
}