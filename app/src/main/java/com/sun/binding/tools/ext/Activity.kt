@file:JvmName("ActivityExt")

package com.sun.binding.tools.ext

import android.app.Activity
import com.blankj.utilcode.util.BarUtils
import com.sun.binding.R
import com.sun.binding.tools.tool.getColor

/**
 * 设置白色状态栏
 */
fun Activity.setWhiteStatusBar() {
    BarUtils.setStatusBarColor(this, R.color.app_white.getColor())
    BarUtils.setStatusBarLightMode(this, true)
}

/**
 * 设置主色调状态栏
 */
fun Activity.setMainColorStatusBar() {
    BarUtils.setStatusBarColor(this, R.color.app_color_main.getColor())
    BarUtils.setStatusBarLightMode(this, true)
}