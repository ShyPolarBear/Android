package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.initChoices
import com.shypolarbear.presentation.util.setReviewMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizViewModel by viewModels()
    private lateinit var dialog: QuizDialog
    override fun initView() {
        dialog = QuizDialog(requireContext())
        val state: DialogType = DialogType.INCORRECT // viewModel로 갈 예정

        binding.apply {
            quizDailyBtnBack.setReviewMode(state, quizDailyPages, dialog, R.id.action_quizDailyOXFragment_to_quizMainFragment)

            quizDailyBtnSubmit.setOnClickListener {
                dialog.showDialog(state)
            }

            val choiceList = listOf(quizDailyO, quizDailyX)
            initChoices(choiceList)
        }
    }
}