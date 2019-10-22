package com.sun.binding.mvvm.model

/**
 * 进度条弹窗控制 Model
 */
data class ProgressModel
/**
 * 主构造函数
 *
 * @param show 是否显示
 * @param cancelable 能否取消
 */
constructor(
        var show: Boolean = true,
        var cancelable: Boolean = true
)