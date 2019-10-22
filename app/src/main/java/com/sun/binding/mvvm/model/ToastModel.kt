package com.sun.binding.mvvm.model

import android.widget.Toast

/**
 * Toast 弹窗控制 Model
 */
data class ToastModel
/**
 * 主构造函数
 *
 * @param msg 显示的文本
 * @param duration 显示的时长
 */
constructor(
    var msg: String,
    var duration: Int = Toast.LENGTH_SHORT
)