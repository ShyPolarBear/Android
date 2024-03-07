package com.beeeam.quiz.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.beeeam.quiz.databinding.DialogQuizStopBinding

class BackDialog(private val context: Context) {
    lateinit var alertDialog: AlertDialog

    fun showDialog() {
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

    private fun initDialog(binding: ViewDataBinding) {
        val dialogBuilder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(false)
        alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }
}
