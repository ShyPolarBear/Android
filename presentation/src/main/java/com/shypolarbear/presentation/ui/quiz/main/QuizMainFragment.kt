package com.shypolarbear.presentation.ui.quiz.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizMainBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.main.QuizMainAdapter.Companion.initAdapter
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.setSpecificTextColor
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

const val QUIZ_TIME = 17
@AndroidEntryPoint
class QuizMainFragment :
    BaseFragment<FragmentQuizMainBinding, QuizViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizViewModel by viewModels()

    override fun initView() {
        viewModel.getAccessToken()

        binding.apply {
            val userName = "춘식이"
            var solvedState = false
            Timber.tag("AC CALL").d(viewModel.tokens.value)
            quizMainTvName.setSpecificTextColor(
                getString(R.string.quiz_main_user_name, userName),
                userName,
                styleId = R.style.H3,
                colorId = R.color.Blue_01
            )
            quizMainTvTitle.setSpecificTextColor(
                getString(R.string.quiz_main_title),
                getString(R.string.quiz_main_polarbear),
                colorId = R.color.Blue_01
            )
            setAdapter()

            quizMainBtnGoQuiz.setOnClickListener {
                viewModel.requestQuiz()
//                when (getQuizFromServer()) {
//                    QuizType.MULTI -> findNavController().navigate(R.id.action_quizMainFragment_to_quizDailyMultiChoiceFragment)
//                    QuizType.OX -> findNavController().navigate(R.id.action_quizMainFragment_to_quizDailyOXFragment)
//                }
            }
        }
    }

    private fun setAdapter() {
        val items = listOf<String>("A", "B", "C", "A", "B", "C", "A", "B", "C", "F")
        val adapter = initAdapter(items)
        binding.quizMainRv.adapter = adapter
    }

    private fun getQuizFromServer(): QuizType {
        val type = "MULTIPLE_CHOICE" // from API
        return when(type){
            QuizType.OX.type -> QuizType.OX
            else -> QuizType.MULTI
        }
    }
}