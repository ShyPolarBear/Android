package com.shypolarbear.presentation.ui.signup.custom

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object CustomFunctions {
    fun setTextColor(context: Context, v: TextView, color: Int){
        v.setTextColor(
            ContextCompat.getColor(
            context,
            color
        ))
    }
    fun Fragment.hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}