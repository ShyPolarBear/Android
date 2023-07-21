package com.shypolarbear.presentation.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalFragment
import com.skydoves.powermenu.PowerMenuItem

fun Button.checkLike(isLike: Boolean, view: Button) {

    val likeBtnOn = ContextCompat.getDrawable(context, R.drawable.ic_btn_like_on)
    val likeBtnOff = ContextCompat.getDrawable(context, R.drawable.ic_btn_like_off)

    if (isLike) {
        view.background = likeBtnOn
    } else {
        view.background = likeBtnOff
    }
}

fun ImageView.setMenu(
    view: ImageView,
    menuList: List<PowerMenuItem>,
    viewLifecycleOwner: LifecycleOwner
) {
    PowerMenuUtil.getPowerMenu(
        context,
        viewLifecycleOwner,
        menuList
    ) .showAsDropDown(
        view,
        FeedTotalFragment.POWER_MENU_OFFSET_X,
        FeedTotalFragment.POWER_MENU_OFFSET_Y
    )
}

fun TextView.setTextColorById(context: Context, colorId: Int) {
    this.setTextColor(
        ContextCompat.getColor(
            context,
            colorId
        )
    )
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