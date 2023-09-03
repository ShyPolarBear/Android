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
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.EventObserver
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.detectActivation
import com.shypolarbear.presentation.util.setReviewMode
import timber.log.Timber

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
    override val viewModel: QuizViewModel by activityViewModels()
    lateinit var dialog: QuizDialog

    /* TODO:
    *  손 봐야야 할 것
    *  복습문제 조회, 채점,
    *  리뷰모드 시 뒤로가기
    *  복습문제 다음문제로 넘어가기
    * */
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
        Timber.tag("PAGE")
            .d("page: ${viewModel.reviewQuizPage.value!! + 1}\n count: ${viewModel.reviewResponse.value!!.peekContent().count} isLast: ${viewModel.reviewQuizPage.value!! + 1 == viewModel.reviewResponse.value!!.peekContent().count}")

        viewModel.getQuizInstance()

        dialog.alertDialog.setOnDismissListener {
            when (state) {
                DialogType.REVIEW -> {
                    viewModel.goNextPage()
                    if (viewModel.reviewQuizPage.value == viewModel.reviewResponse.value!!.peekContent().count) {
                        findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
                    } else {
                        viewModel.getQuizInstance()
                        when (viewModel.quizInstance.value!!.type) {
                            QuizType.MULTI.type -> findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_self)
                            QuizType.OX.type -> findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_quizDailyOXFragment)
                        }
                    }
                }

                DialogType.DEFALUT -> {
                    findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main)
                }
            }
        }

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
            val choiceList =
                listOf(quizDailyChoice1, quizDailyChoice2, quizDailyChoice3, quizDailyChoice4)

            choiceList.map { choice ->
                choice.setOnClickListener {
                    val id = viewModel.quizInstance.value!!.choices!![choiceList.indexOf(choice)].id
                    viewModel.setAnswer(id.toString())

                    choice.detectActivation(*choiceList.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }

            quizDailyProblem.text = viewModel.quizInstance.value!!.question
            initChoices(choiceList, viewModel.quizInstance.value!!.choices!!)

            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                backBtn,
                R.id.action_quizDailyMultiChoiceFragment_to_navigation_quiz_main
            )

            quizDailyBtnSubmit.setOnClickListener {
                viewModel.submitAnswer(QuizType.MULTI)
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
}