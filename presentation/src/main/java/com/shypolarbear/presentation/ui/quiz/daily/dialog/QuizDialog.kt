package com.shypolarbear.presentation.ui.quiz.daily.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.databinding.DialogQuizResultBinding
import com.shypolarbear.presentation.util.DialogType

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
            context.getString(R.string.quiz_dialog_point, point)

        binding.quizDialogBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        when (dialogType) {
            DialogType.REVIEW -> {
                binding.quizDailyTvSubmit.text = when (isLast) {
                    false -> context.getString(R.string.quiz_dialog_next)
                    else -> { context.getString(R.string.quiz_dialog_confirm) }
                }
                binding.tvQuizDialogPoint.isVisible = false
            }

            DialogType.DEFAULT -> {
            }
        }
        when (isCorrect) {
            true -> {
                binding.ivQuizDialog.setImageResource(R.drawable.ic_signup_success)
            }

            false -> {
                binding.ivQuizDialog.setImageResource(R.drawable.ic_quiz_incorrect)
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
