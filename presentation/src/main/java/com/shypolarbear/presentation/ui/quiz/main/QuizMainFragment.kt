package com.shypolarbear.presentation.ui.quiz.main

import androidx.fragment.app.activityViewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizMainBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.main.QuizMainAdapter.Companion.initAdapter
import com.shypolarbear.presentation.util.EventObserver
import com.shypolarbear.presentation.util.QuizNavType
import com.shypolarbear.presentation.util.setQuizNavigation
import com.shypolarbear.presentation.util.setSpecificTextColor
import dagger.hilt.android.AndroidEntryPoint

const val MAX_PAGES = 5

@AndroidEntryPoint
class QuizMainFragment :
    BaseFragment<FragmentQuizMainBinding, QuizViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizViewModel by activityViewModels()

    override fun initView() {
        viewModel.requestDailyQuizSolvedState()
        viewModel.getMyInfo()
        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            userName?.let {
                binding.quizMainTvName.setSpecificTextColor(
                    getString(R.string.quiz_main_user_name, userName),
                    userName,
                    styleId = R.style.H3,
                    colorId = R.color.Blue_01
                )
            }
        }
        viewModel.dailySubmit.observe(viewLifecycleOwner) { solvedState ->
            if (solvedState) {
                binding.quizMainTvGoQuiz.text = getString(R.string.quiz_main_tv_go_quiz)
            }
        }
        viewModel.quizResponse.observe(viewLifecycleOwner, EventObserver { quiz ->
            setQuizNavigation(quiz.type, QuizNavType.MAIN)
        })
        viewModel.reviewResponse.observe(viewLifecycleOwner, EventObserver { reviewQuiz ->
            setQuizNavigation(reviewQuiz.content.first().type, QuizNavType.MAIN)
        })

        binding.apply {
            quizMainTvTitle.setSpecificTextColor(
                getString(R.string.quiz_main_title),
                getString(R.string.quiz_main_polarbear),
                colorId = R.color.Blue_01
            )
            setAdapter()

            quizMainBtnGoQuiz.setOnClickListener {
                if (viewModel.dailySubmit.value!!) {
                    viewModel.requestReviewQuiz()
                } else viewModel.requestQuiz()
            }
        }
    }

    private fun setAdapter() {
        val items = listOf<String>("A", "B", "C", "A", "B", "C", "A", "B", "C", "F")
        val adapter = initAdapter(items)
        binding.quizMainRv.adapter = adapter
    }
}