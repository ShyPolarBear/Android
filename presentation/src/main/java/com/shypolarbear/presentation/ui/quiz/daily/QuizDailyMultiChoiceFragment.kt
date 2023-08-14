package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyMultiBinding
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.setQuizBackButton

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizDailyViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
    private lateinit var dialog: QuizDialog
    override val viewModel: QuizDailyViewModel by viewModels()

    override fun initView() {
        dialog = QuizDialog(requireContext())
        val state: DialogType = DialogType.CORRECT // viewModel로 갈 예정

        binding.apply {
            quizDailyBtnBack.setQuizBackButton(state, dialog)
            quizDailyBtnSubmit.setOnClickListener {
                dialog.showDialog(state, )
            }
        }
    }
}