package com.shypolarbear.presentation.util

import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalFragment
import com.skydoves.powermenu.PowerMenuItem

fun Button.showLike(isLike: Boolean, view: Button) {

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