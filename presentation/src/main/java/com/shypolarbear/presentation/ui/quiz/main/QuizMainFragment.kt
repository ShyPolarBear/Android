package com.shypolarbear.presentation.ui.quiz.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizMainBinding
import com.shypolarbear.presentation.ui.quiz.main.QuizMainAdapter.Companion.initAdapter
import com.shypolarbear.presentation.util.setSpecificTextColor

class QuizMainFragment :
    BaseFragment<FragmentQuizMainBinding, QuizMainViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizMainViewModel by viewModels()

    override fun initView() {
        binding.apply {
            val userName = "춘식이"
            var solvedState = false

            quizMainTvName.setSpecificTextColor(getString(R.string.quiz_main_user_name, userName), userName, styleId = R.style.H3, colorId = R.color.Blue_01)
            quizMainTvTitle.setSpecificTextColor(getString(R.string.quiz_main_title), "북극곰", colorId = R.color.Blue_01)
            setAdapter()

            quizMainBtnGoQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_quizMainFragment_to_quizDailyMultiChoiceFragment)
            }
        }
    }

    private fun setAdapter(){
        val items = listOf<String>("A","B","C", "A","B","C","A","B","C","F")
        val adapter = initAdapter(items)
        binding.quizMainRv.adapter = adapter
    }
}