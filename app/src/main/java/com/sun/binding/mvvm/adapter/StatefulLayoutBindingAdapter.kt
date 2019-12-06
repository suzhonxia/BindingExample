package com.sun.binding.mvvm.adapter

import androidx.databinding.BindingAdapter
import com.sun.binding.tools.tool.setCommonMode
import com.sun.binding.tools.tool.setLocationMode
import com.sun.binding.widget.state.StateEnum
import com.sun.binding.widget.state.StateMode
import com.sun.binding.widget.state.StatefulLayout

/**
 * StatefulLayout 扩展
 */

/**
 * 设置显示模式
 */
@BindingAdapter("android:bind_sfl_mode", "android:bind_sfl_retry", requireAll = true)
fun setStateMode(sfl: StatefulLayout, mode: StateMode, retry: () -> Unit) {
    when (mode) {
        StateMode.location -> sfl.setLocationMode(retry)
        else -> sfl.setCommonMode(retry)
    }
}

/**
 * 设置状态
 */
@BindingAdapter("android:bind_sfl_state")
fun setViewState(sfl: StatefulLayout, state: StateEnum) {
    sfl.viewState = state
}