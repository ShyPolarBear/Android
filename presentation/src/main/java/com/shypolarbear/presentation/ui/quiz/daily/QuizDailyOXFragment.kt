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
import com.shypolarbear.presentation.ui.quiz.main.MAX_PAGES
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.EventObserver
import com.shypolarbear.presentation.util.QuizNavType
import com.shypolarbear.presentation.util.detectActivation
import com.shypolarbear.presentation.util.initProgressBar
import com.shypolarbear.presentation.util.setQuizNavigation
import com.shypolarbear.presentation.util.setReviewMode
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer

@AndroidEntryPoint
class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizViewModel by activityViewModels()
    private lateinit var dialog: QuizDialog
    private lateinit var backBtn: BackDialog
    private lateinit var progressJob: Timer
    private var pageEnd: Int = 0

    override fun initView() {
        val state = checkReviewMode()
        backBtn = BackDialog(requireContext())
        dialog = QuizDialog(requireContext(), state)
        viewModel.getQuizInstance()
        viewModel.initAnswer()

        dialog.alertDialog.setOnDismissListener {
            when (state) {
                DialogType.REVIEW -> {
                    viewModel.goNextPage()
                    if (viewModel.reviewQuizPage.value == pageEnd) {
                        findNavController().navigate(R.id.action_quizDailyOXFragment_to_navigation_quiz_main)
                    } else {
                        viewModel.getQuizInstance()
                        setQuizNavigation(viewModel.quizInstance.value!!.type, QuizNavType.OX)
                    }
                }

                DialogType.DEFAULT -> {
                    findNavController().navigate(R.id.action_quizDailyOXFragment_to_navigation_quiz_main)
                }
            }
        }

        viewModel.submitResponse.observe(viewLifecycleOwner, EventObserver { response ->
            when (state) {
                DialogType.REVIEW -> {
                    dialog.showDialog(
                        response.isCorrect,
                        response.explanation,
                        response.point.toInt(),
                        viewModel.reviewQuizPage.value!! + 1 == pageEnd
                    )
                }

                DialogType.DEFAULT -> {
                    dialog.showDialog(
                        response.isCorrect,
                        response.explanation,
                        response.point.toInt(),
                    )
                }
            }
        })

        binding.apply {
            quizDailyProblem.text = viewModel.quizInstance.value!!.question
            val choiceList = listOf(quizDailyO, quizDailyX)

            progressJob =
                quizDailyProgressBar.initProgressBar(quizDailyTvTime) {
                    viewModel.submitAnswer(
                        isTimeOut = true
                    )
                }
            choiceList.map { choice ->
                choice.setOnClickListener {
                    val answer = choice.text.toString()
                    viewModel.setAnswer(answer)
                    quizDailyBtnSubmit.isActivated = choice.isActivated.not()
                    choice.detectActivation(*choiceList.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }

            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                backBtn,
                R.id.action_quizDailyOXFragment_to_navigation_quiz_main,
                progressJob
            )

            quizDailyBtnSubmit.setOnClickListener {
                viewModel.answerId.value?.let {
                    progressJob.cancel()
                    viewModel.submitAnswer()
                }
            }
        }
    }

    override fun onBackPressed() {
        when(checkReviewMode()) {
            DialogType.REVIEW -> {
                backBtn.showDialog()
                backBtn.alertDialog.setOnCancelListener {
                    progressJob.cancel()
                    findNavController().navigate(R.id.action_quizDailyOXFragment_to_navigation_quiz_main)
                }

            }
            DialogType.DEFAULT -> {
                progressJob.cancel()
                findNavController().navigate(R.id.action_quizDailyOXFragment_to_navigation_quiz_main)
            }
        }
    }

    private fun checkReviewMode(): DialogType {
        return if (viewModel.dailySubmit.value == true) {
            binding.quizDailyPages.isVisible = true
            pageEnd = if (viewModel.reviewResponse.value!!.peekContent().count > 5) {
                MAX_PAGES
            } else {
                viewModel.reviewResponse.value!!.peekContent().count
            }
            binding.quizDailyPages.text = getString(
                R.string.quiz_page_indicator,
                viewModel.reviewQuizPage.value!! + 1,
                pageEnd
            )
            DialogType.REVIEW
        } else {
            DialogType.DEFAULT
        }
    }
}