package com.beeeam.quiz

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.beeeam.base.BaseFragment
import com.beeeam.quiz.dialog.BackDialog
import com.beeeam.quiz.dialog.QuizDialog
import com.beeeam.quiz.databinding.FragmentQuizDailyOxBinding
import com.beeeam.util.Const.MAX_PAGES
import com.beeeam.util.DialogType
import com.beeeam.util.EventObserver
import com.beeeam.util.QuizNavType
import com.beeeam.util.detectActivation
import com.beeeam.util.initProgressBar
import com.beeeam.util.setQuizNavigation
import com.beeeam.util.setReviewMode
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

        viewModel.submitResponse.observe(
            viewLifecycleOwner,
            EventObserver { response ->
                when (state) {
                    DialogType.REVIEW -> {
                        dialog.showDialog(
                            response.isCorrect,
                            response.explanation,
                            response.point.toInt(),
                            viewModel.reviewQuizPage.value!! + 1 == pageEnd,
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
            },
        )

        binding.apply {
            quizDailyProblem.text = viewModel.quizInstance.value!!.question
            val choiceList = listOf(quizDailyO, quizDailyX)

            progressJob =
                quizDailyProgressBar.initProgressBar(quizDailyTvTime) {
                    viewModel.submitAnswer(
                        isTimeOut = true,
                    )
                }
            choiceList.map { choice ->
                choice.setOnClickListener {
                    val answer = choice.text.toString()
                    viewModel.setAnswer(answer)
                    quizDailyBtnSubmit.isActivated = choice.isActivated.not()
                    choice.detectActivation(
                        *choiceList.filter { other ->
                            other != choice
                        }.toTypedArray(),
                    )
                }
            }

            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                backBtn,
                R.id.action_quizDailyOXFragment_to_navigation_quiz_main,
                progressJob,
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
        when (checkReviewMode()) {
            DialogType.REVIEW -> {
                backBtn.showDialog()
                backBtn.alertDialog.setOnCancelListener {
                    progressJob.cancel()
                    findNavController().navigate(R.id.action_quizDailyOXFragment_to_navigation_quiz_main)
                }
            }
            DialogType.DEFAULT -> {
                progressJob.cancel()
                findNavController().popBackStack()
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
                com.beeeam.designsystem.R.string.quiz_page_indicator,
                viewModel.reviewQuizPage.value!! + 1,
                pageEnd,
            )
            DialogType.REVIEW
        } else {
            DialogType.DEFAULT
        }
    }
}
