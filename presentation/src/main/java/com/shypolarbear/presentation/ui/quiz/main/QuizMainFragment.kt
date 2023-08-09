package com.shypolarbear.presentation.ui.quiz.main

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizMainBinding
import com.shypolarbear.presentation.util.setSpecificTextColor
import com.shypolarbear.presentation.util.NAME

class QuizMainFragment :
    BaseFragment<FragmentQuizMainBinding, QuizMainViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizMainViewModel by viewModels()

    override fun initView() {
        binding.apply {
            val userName = "춘식이"

            quizMainTvName.setSpecificTextColor(getString(R.string.quiz_main_user_name, userName), userName, NAME)
            quizMainTvTitle.setSpecificTextColor(getString(R.string.quiz_main_title), "북극곰")

        }
    }
}