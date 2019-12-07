package com.sun.binding.mvvm.adapter

import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.LogUtils
import com.hjq.bar.TitleBar

/**
 * TitleBar 扩展
 */

/**
 * 设置标题
 */
@BindingAdapter("android:bind_tb_title")
fun setTitle(tb: TitleBar, title: CharSequence?) {
    LogUtils.d("setTitle title = $title")
    title?.let {
        tb.setTitle(it)
    }
}