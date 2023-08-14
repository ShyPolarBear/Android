package com.shypolarbear.presentation.ui.quiz.daily

import android.content.Context
import android.content.DialogInterface
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import timber.log.Timber

class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizDailyViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizDailyViewModel by viewModels()

    override fun initView() {
        val dialog = QuizDialog(requireContext())
        binding.apply {
            quizDailyBtnSubmit.setOnClickListener {
                dialog.showDialog(DialogType.INCORRECT, "s")
            }
            quizDailyBtnBack.setOnClickListener {
                dialog.showDialog(DialogType.REVIEW)
                dialog.alertDialog.setOnCancelListener {
                    findNavController().navigate(R.id.action_quizDailyOXFragment_to_quizMainFragment)
                }
            }
        }
    }
}