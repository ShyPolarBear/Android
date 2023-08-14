package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyMultiBinding

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizDailyViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
    override val viewModel: QuizDailyViewModel by viewModels()
    override fun initView() {

    }
}