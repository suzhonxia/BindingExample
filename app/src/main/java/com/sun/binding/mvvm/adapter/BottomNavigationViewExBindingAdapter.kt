@file:Suppress("unused")

package com.sun.binding.mvvm.adapter

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.sun.binding.tools.ext.condition

/**
 * BottomNavigationViewEx DataBinding 扩展属性
 */

/**
 * 设置 item 切换动画
 *
 * @param bnv [BottomNavigationViewEx] 对象
 * @param enableAnimation 启用或禁用点击项目动画
 */
@BindingAdapter("android:bind_bnv_enable_animation")
fun setNavigationAnimation(bnv: BottomNavigationViewEx, enableAnimation: Boolean) {
    bnv.enableAnimation(enableAnimation.condition)
}

/**
 * 设置 item 切换动画
 *
 * @param bnv [BottomNavigationViewEx] 对象
 * @param iconSize icon 大小(DP)
 * @param textSize 文本字体大小(SP)
 */
@BindingAdapter(
    "android:bind_bnv_icon_size",
    "android:bind_bnv_text_size",
    requireAll = false
)
fun setNavigationAttr(bnv: BottomNavigationViewEx, iconSize: Int?, textSize: Int?) {
    iconSize?.let { bnv.setIconSize(it.toFloat()) }
    textSize?.let { bnv.setTextSize(it.toFloat()) }
}

@BindingAdapter("android:bind_bnv_item_selected_listener")
fun setNavigationItemSelectedListener(bnv: BottomNavigationViewEx, itemSelectedListener: ((MenuItem) -> Boolean)?) {
    itemSelectedListener?.let { bnv.setOnNavigationItemSelectedListener(it) }
}