package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyMultiBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.initChoices
import com.shypolarbear.presentation.util.setReviewMode

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
    private lateinit var dialog: QuizDialog
    override val viewModel: QuizViewModel by viewModels()

    override fun initView() {
        dialog = QuizDialog(requireContext())
        val state: DialogType = DialogType.REVIEW // viewModel로 갈 예정

        binding.apply {
            quizDailyBtnBack.setReviewMode(state, quizDailyPages, dialog, R.id.action_quizDailyMultiChoiceFragment_to_quizMainFragment)

            quizDailyBtnSubmit.setOnClickListener {
                dialog.showDialog(state)
            }
            val choiceList = listOf(quizDailyChoice1, quizDailyChoice2, quizDailyChoice3, quizDailyChoice4)
            initChoices(choiceList)
        }
    }
}