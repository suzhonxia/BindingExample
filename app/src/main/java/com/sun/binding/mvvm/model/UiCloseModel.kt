package com.sun.binding.mvvm.model

import android.app.Activity
import android.content.Intent

/**
 * 关闭 UI 界面, 并返回数据对象给上一个界面
 */
data class UiCloseModel
/**
 * 主构造函数
 *
 * @param resultCode 返回码 默认 [Activity.RESULT_CANCELED]
 * @param result 返回数据 默认 null
 */
constructor(
    var resultCode: Int = Activity.RESULT_CANCELED,
    var result: Intent? = null
)