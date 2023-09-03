package com.shypolarbear.presentation.ui.quiz.daily

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.BackDialog
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.EventObserver
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.detectActivation
import com.shypolarbear.presentation.util.setReviewMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizViewModel by activityViewModels()
    private lateinit var dialog: QuizDialog
    override fun initView() {
        val state = if (viewModel.dailySubmit.value == true) {
            binding.quizDailyPages.isVisible = true
            binding.quizDailyPages.text = getString(
                R.string.quiz_page_indicator,
                viewModel.reviewQuizPage.value!! + 1,
                viewModel.reviewResponse.value!!.peekContent().count
            )
            DialogType.REVIEW
        } else {
            DialogType.DEFALUT
        }
        val backBtn = BackDialog(requireContext())
        dialog = QuizDialog(requireContext(), state)
        dialog.alertDialog.setOnDismissListener {
            when (state) {
                DialogType.REVIEW -> {
                    viewModel.goNextPage()
                    if (viewModel.reviewQuizPage.value == viewModel.reviewResponse.value!!.peekContent().count) {
                        findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
                    } else {
                        viewModel.getQuizInstance()
                        when (viewModel.quizInstance.value!!.type) {
                            QuizType.MULTI.type -> findNavController().navigate(R.id.action_quizDailyOXFragment_to_quizDailyMultiChoiceFragment)
                            QuizType.OX.type -> findNavController().navigate(R.id.action_quizDailyOXFragment_self)
                        }
                    }
                }

                DialogType.DEFALUT -> {
                    findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
                }
            }
        }

        viewModel.getQuizInstance()

        viewModel.submitBtnState.observe(viewLifecycleOwner) { submitState ->
            submitState?.let {
                binding.quizDailyTvSubmit.isActivated = submitState
                binding.quizDailyBtnSubmit.isActivated = submitState
            }
        }

        viewModel.submitResponse.observe(viewLifecycleOwner, EventObserver { response ->
            dialog.showDialog(
                response.isCorrect,
                response.explanation,
                response.point.toInt(),
                viewModel.reviewQuizPage.value!! + 1 == viewModel.reviewResponse.value!!.peekContent().count
            )
        })

        binding.apply {
            quizDailyProblem.text = viewModel.quizInstance.value!!.question
            val choiceList = listOf(quizDailyO, quizDailyX)

            choiceList.map { choice ->
                choice.setOnClickListener {
                    val answer = choice.text.toString()
                    viewModel.setAnswer(answer)

                    choice.detectActivation(*choiceList.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }


            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                backBtn,
                R.id.action_quizDailyOXFragment_to_navigation_quiz_main
            )
            quizDailyBtnSubmit.setOnClickListener {
                viewModel.submitAnswer(QuizType.OX)
            }
        }
    }
}