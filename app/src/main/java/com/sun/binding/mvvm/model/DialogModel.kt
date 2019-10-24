package com.sun.binding.mvvm.model

import android.view.View

/**
 * 进度条弹窗控制 Model
 */
data class DialogModel
/**
 * 主构造函数
 *
 * @param attachView 依附于某个 View 的弹窗
 * @param show 是否显示
 * @param cancelable 点击外部是否关闭 Dialog
 * @param title 标题
 * @param message 内容
 * @param confirmText 确定按钮文本
 * @param cancelText 取消按钮文本
 * @param actionText 中间功能按钮文本
 * @param centerList 居中列表
 * @param bottomList 底部列表
 * @param attachList 依附列表
 * @param confirmListener 确定按钮点击监听
 * @param cancelListener 取消按钮点击监听
 * @param actionListener 中间功能按钮点击监听
 * @param selectListener 列表按钮点击监听
 */
constructor(
    var attachView: View? = null,
    var show: Boolean = true,
    var cancelable: Boolean = false,
    var title: String? = null,
    var message: String? = null,
    var confirmText: String? = null,
    var cancelText: String? = null,
    var actionText: String? = null,
    var centerList: List<String>? = null,
    var bottomList: List<String>? = null,
    var attachList: List<String>? = null,
    var confirmListener: (() -> Unit)? = null,
    var cancelListener: (() -> Unit)? = null,
    var actionListener: (() -> Unit)? = null,
    var selectListener: ((String) -> Unit)? = null
)