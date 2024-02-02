package com.shypolarbear.presentation.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu

object PowerMenuUtil {

    private const val MENU_RADIUS = 18f
    private const val MENU_WIDTH = 350
    private const val MENU_PADDING = 14
    private const val MENU_TEXT_SIZE = 18

    fun getPowerMenu(
        context: Context,
        lifecycle: LifecycleOwner,
        items: List<PowerMenuItem>,
        onItemClickListener: OnMenuItemClickListener<PowerMenuItem>,
    ): PowerMenu {
        return createPowerMenu(context) {
            addItemList(items)
            setWidth(MENU_WIDTH)
            setTextSize(MENU_TEXT_SIZE)
            setMenuRadius(MENU_RADIUS)
            setPadding(MENU_PADDING)
            setLifecycleOwner(lifecycle)
            setAutoDismiss(true)
            setOnMenuItemClickListener(onItemClickListener)
            build()
        }
    }
}
