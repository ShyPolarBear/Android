package com.beeeam.more.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.beeeam.myinfo.databinding.DialogMoreLogoutBinding

class LogOutDialog(private val context: Context) {
    lateinit var alertDialog: AlertDialog
    fun showDialog() {
        val binding = DialogMoreLogoutBinding.inflate(LayoutInflater.from(context), null, false)
        initDialog(binding)

        binding.apply {
            btnMoreLogoutYes.setOnClickListener {
                alertDialog.cancel()
            }
            btnMoreLogoutNo.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }

    private fun initDialog(binding: ViewDataBinding) {
        val dialogBuilder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(true)
        alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }
}
