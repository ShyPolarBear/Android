package com.beeeam.quiz.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.beeeam.quiz.databinding.DialogQuizResultBinding
import com.beeeam.util.DialogType

class QuizDialog(
    private val context: Context,
    private val dialogType: DialogType,
) {
    lateinit var alertDialog: AlertDialog
    val binding = DialogQuizResultBinding.inflate(LayoutInflater.from(context), null, false)

    init {
        initDialog()
    }

    fun showDialog(
        isCorrect: Boolean,
        explain: String,
        point: Int,
        isLast: Boolean? = null,
    ) {
        binding.tvQuizDialogExplain.text = explain
        binding.tvQuizDialogPoint.text =
            context.getString(com.beeeam.designsystem.R.string.quiz_dialog_point, point)

        binding.quizDialogBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        when (dialogType) {
            DialogType.REVIEW -> {
                binding.quizDailyTvSubmit.text = when (isLast) {
                    false -> context.getString(com.beeeam.designsystem.R.string.quiz_dialog_next)
                    else -> { context.getString(com.beeeam.designsystem.R.string.quiz_dialog_confirm) }
                }
                binding.tvQuizDialogPoint.isVisible = false
            }

            DialogType.DEFAULT -> {
            }
        }
        when (isCorrect) {
            true -> {
                binding.ivQuizDialog.setImageResource(com.beeeam.designsystem.R.drawable.ic_signup_success)
            }

            false -> {
                binding.ivQuizDialog.setImageResource(com.beeeam.designsystem.R.drawable.ic_quiz_incorrect)
            }
        }
        alertDialog.show()
    }

    private fun initDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(false)
        alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
