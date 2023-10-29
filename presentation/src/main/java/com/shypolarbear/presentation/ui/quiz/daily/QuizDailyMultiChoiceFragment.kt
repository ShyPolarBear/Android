package com.shypolarbear.presentation.ui.quiz.daily

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.domain.model.quiz.Choice
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyMultiBinding
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
import java.util.Timer

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
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
                        findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
                    } else {
                        viewModel.getQuizInstance()
                        setQuizNavigation(viewModel.quizInstance.value!!.type, QuizNavType.MULTI)
                    }
                }

                DialogType.DEFAULT -> {
                    findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
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
            val choiceList =
                listOf(quizDailyChoice1, quizDailyChoice2, quizDailyChoice3, quizDailyChoice4)
            progressJob = quizDailyProgressBar.initProgressBar(
                quizDailyTvTime
            ) { viewModel.submitAnswer(isTimeOut = true) }

            viewModel.quizInstance.value?.let { quizzes ->
                choiceList.map { choice ->
                    choice.setOnClickListener {
                        val id = quizzes.choices!![choiceList.indexOf(choice)].id
                        viewModel.setAnswer(id.toString())
                        quizDailyBtnSubmit.isActivated = choice.isActivated.not()
                        choice.detectActivation(*choiceList.filter { other ->
                            other != choice
                        }.toTypedArray())
                    }
                }

                quizDailyProblem.text = quizzes.question
                initChoices(choiceList, quizzes.choices)
            }

            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                backBtn,
                R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main,
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
                    findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
                }

            }
            DialogType.DEFAULT -> {
                progressJob.cancel()
                findNavController().popBackStack()
            }
        }
    }

    private fun initChoices(choiceList: List<TextView>, textList: List<Choice>? = null) {
        for ((answerText, choice) in choiceList.withIndex()) {
            textList?.let {
                choice.text = textList[answerText].text
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