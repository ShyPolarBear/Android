package com.shypolarbear.presentation.ui.quiz.daily

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.setQuizBackButton
import timber.log.Timber

class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizDailyViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizDailyViewModel by viewModels()
    private lateinit var dialog: QuizDialog
    override fun initView() {
        dialog = QuizDialog(requireContext())
        val state: DialogType = DialogType.REVIEW // viewModel로 갈 예정

        binding.apply {
            quizDailyBtnBack.setQuizBackButton(state, dialog)
            quizDailyBtnSubmit.setOnClickListener {
                dialog.showDialog(state, )
            }
        }
    }
}