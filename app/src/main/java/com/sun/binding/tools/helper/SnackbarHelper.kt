package com.sun.binding.tools.helper

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.sun.binding.mvvm.model.SnackbarModel

object SnackbarHelper {

    /**
     * 显示 Snackbar
     *
     * @param root   Activity 对象
     * @param snackbarModel Snackbar 配置 Model
     */
    fun show(root: View, snackbarModel: SnackbarModel) {
        if (snackbarModel.content.isNullOrBlank()) {
            return
        }
        val snackBar = Snackbar.make(root, snackbarModel.content.orEmpty(), snackbarModel.duration)
        snackBar.setTextColor(snackbarModel.contentColor)
        snackBar.setBackgroundTint(snackbarModel.contentBgColor)
        if (snackbarModel.actionText != null && snackbarModel.onAction != null) {
            snackBar.setAction(snackbarModel.actionText, snackbarModel.onAction)
            snackBar.setActionTextColor(snackbarModel.actionColor)
        }
        if (snackbarModel.onCallback != null) {
            snackBar.addCallback(snackbarModel.onCallback)
        }
        snackBar.show()
    }
}