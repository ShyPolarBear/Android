package com.shypolarbear.presentation.ui.quiz.main

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizMainBinding

class QuizMainFragment: BaseFragment<FragmentQuizMainBinding, QuizMainViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizMainViewModel by viewModels()

    override fun initView() {
        binding.apply {

        }
    }
}