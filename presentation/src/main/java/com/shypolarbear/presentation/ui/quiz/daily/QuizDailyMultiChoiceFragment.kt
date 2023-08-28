package com.shypolarbear.presentation.ui.quiz.daily

import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.shypolarbear.domain.model.quiz.Choice
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyMultiBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.detectActivation
import com.shypolarbear.presentation.util.setReviewMode
import timber.log.Timber

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
    private lateinit var dialog: QuizDialog
    override val viewModel: QuizViewModel by activityViewModels()

    override fun initView() {
        dialog = QuizDialog(requireContext())
        val state: DialogType = DialogType.CORRECT // viewModel로 갈 예정

        viewModel.submitBtnState.observe(viewLifecycleOwner) { state ->
            state?.let {
                binding.quizDailyTvSubmit.isActivated = state
                binding.quizDailyBtnSubmit.isActivated = state
            }
        }

        binding.apply {
            val choiceList =
                listOf(quizDailyChoice1, quizDailyChoice2, quizDailyChoice3, quizDailyChoice4)

            choiceList.map { choice ->
                choice.setOnClickListener {
                    viewModel.setAnswer(choice.text.toString())
                    choice.detectActivation(*choiceList.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }

            viewModel.quizResponse.observe(viewLifecycleOwner) { quiz ->
                quiz?.let {
                    quizDailyProblem.text = quiz.question
                    initChoices(choiceList, quiz.choices!!)
                }
            }

            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                dialog,
                R.id.action_quizDailyMultiChoiceFragment_to_quizMainFragment
            )
            quizDailyBtnSubmit.setOnClickListener {
                dialog.showDialog(state)
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