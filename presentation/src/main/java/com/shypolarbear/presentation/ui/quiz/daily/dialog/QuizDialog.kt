package com.shypolarbear.presentation.ui.quiz.daily.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.DialogQuizResultBinding
import com.shypolarbear.presentation.databinding.DialogQuizStopBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.main.QuizMainFragment
import com.shypolarbear.presentation.util.DialogType

class QuizDialog(
    private val context: Context,
){
    lateinit var alertDialog: AlertDialog
    // dialog에서 뷰모델 사용할 수 있게 수정

    fun showDialog(
        dialogType: Boolean,
        explain: String? = null,
        point: String = DialogType.INCORRECT.point,
    ) {
        val type = when (dialogType){
            true -> DialogType.CORRECT
            false -> DialogType.INCORRECT
        }
        when (type) {
            DialogType.REVIEW -> {
                val binding =
                    DialogQuizStopBinding.inflate(LayoutInflater.from(context), null, false)
                initDialog(binding)

                binding.quizDialogBtnYes.setOnClickListener {
                    alertDialog.cancel()
                }
                binding.quizDialogBtnNo.setOnClickListener {
                    alertDialog.dismiss()
                }
            }

            DialogType.CORRECT, DialogType.INCORRECT -> {
                val binding =
                    DialogQuizResultBinding.inflate(LayoutInflater.from(context), null, false)
                initDialog(binding)
                binding.tvQuizDialogExplain.text = explain
                if (type == DialogType.INCORRECT) {
                    binding.ivQuizDialog.setImageResource(R.drawable.ic_quiz_incorrect)
                }
                binding.tvQuizDialogPoint.text =
                    context.getString(R.string.quiz_dialog_point, point)
                binding.quizDialogBtn.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }
    }

    private fun initDialog(binding: ViewDataBinding) {
        val dialogBuilder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(false)
        alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }
}