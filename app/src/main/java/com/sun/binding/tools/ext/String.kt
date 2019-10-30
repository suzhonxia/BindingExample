@file:JvmName("StringExt")

package com.sun.binding.tools.ext

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.mvvm.model.ToastModel
import com.sun.binding.tools.helper.SnackbarHelper

fun String?.toToastMsg(): ToastModel {
    return ToastModel(this.orEmpty())
}

fun String?.toSnackbarMsg(): SnackbarModel {
    return SnackbarModel(this.orEmpty())
}

fun String?.showToast() {
    if (!this.isNullOrEmpty()) {
        ToastUtils.showShort(this)
    }
}

fun String?.showSnackbar(root: View) {
    if (!this.isNullOrEmpty()) {
        SnackbarHelper.show(root, this.toSnackbarMsg())
    }
}