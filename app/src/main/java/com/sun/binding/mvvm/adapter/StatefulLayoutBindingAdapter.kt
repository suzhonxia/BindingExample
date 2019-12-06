package com.sun.binding.mvvm.adapter

import androidx.databinding.BindingAdapter
import com.sun.binding.widget.state.StateEnum
import com.sun.binding.widget.state.StatefulLayout

/**
 * StatefulLayout 扩展
 */

/**
 * 设置状态
 */
@BindingAdapter("android:bind_sfl_state")
fun setViewState(sfl: StatefulLayout, state: StateEnum) {
    sfl.viewState = state
}