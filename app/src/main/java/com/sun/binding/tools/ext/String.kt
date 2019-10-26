@file:JvmName("StringExt")

package com.sun.binding.tools.ext

import com.blankj.utilcode.util.ToastUtils
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.mvvm.model.ToastModel

fun String?.toToastMsg(): ToastModel {
    return ToastModel(this.orEmpty())
}

fun String?.toSnackbarMsg(): SnackbarModel {
    return SnackbarModel(this.orEmpty())
}

fun String?.showToast() {
    this?.let { ToastUtils.showShort(it) }
}