package com.shypolarbear.presentation.ui.feed

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.shypolarbear.presentation.R
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu


class FeedMenuFactory : PowerMenu.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner): PowerMenu {
        return createPowerMenu(context) {
            addItem(PowerMenuItem("최신", false))
            addItem(PowerMenuItem("최근 인기", false))
            addItem(PowerMenuItem("best", false))
            setWidth(350)
            setMenuRadius(18f)
            setLifecycleOwner(lifecycle)
            setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
            setTextColor(ContextCompat.getColor(context, R.color.Black_01))
            setTextSize(18)
            setTextGravity(Gravity.LEFT)
            setMenuColor(Color.WHITE)
            setInitializeRule(Lifecycle.Event.ON_CREATE, 0)
        }
    }
}