package com.sun.binding.tools.helper

import android.app.Activity
import com.sun.binding.mvvm.model.DialogModel

/**
 * XPopup 封装使用帮助类
 */
object XPopupDialogHelper {

    /**
     * 显示弹窗
     *
     * @param activity   Activity 对象
     * @param dialogModel Dialog 配置 Model
     */
    fun show(activity: Activity, dialogModel: DialogModel) {
//        if (AppStackManager.getContext().isMainProcess) {
//            // 主线程
//            ProgressDialogHelper.showDialog(activity, cancelable)
//        } else {
//            // 子线程
//            MainThreadManager.postToMainThread(Runnable {
//                ProgressDialogHelper.showDialog(activity, cancelable)
//            })
//        }
    }

    /**
     * 隐藏弹窗
     *
     * @param dialogModel Dialog 配置 Model
     */
    fun dismiss(dialogModel: DialogModel) {
//        if (AppStackManager.getContext().isMainProcess) {
//            // 主线程
//            ProgressDialogHelper.dismissDialog()
//        } else {
//            // 子线程
//            runOnMainThread(Runnable {
//                ProgressDialogHelper.dismissDialog()
//            })
//        }
    }
}