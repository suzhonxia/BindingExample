package com.sun.binding.tools.helper

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import com.blankj.utilcode.util.LogUtils
import com.sun.binding.R
import com.sun.binding.databinding.AppDialogProgressBinding
import com.sun.binding.mvvm.model.ProgressModel
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.isMainProcess
import com.sun.binding.tools.ext.runOnMainThread
import com.sun.binding.tools.manager.AppManager
import com.sun.binding.tools.manager.MainThreadManager
import com.sun.binding.tools.tool.getString

/**
 * 加载进度条帮助类
 */
object ProgressDialogHelper {

    /** 弹窗对象  */
    private var mDialog: Dialog? = null

    /**
     * 显示弹窗
     *
     * @param activity   Activity 对象
     * @param progress Progress 配置 Model
     */
    private fun showDialog(activity: Activity, progress: ProgressModel) {
        // 显示前先隐藏
        dismissDialog()

        // 加载布局
        val binding = AppDialogProgressBinding.inflate(LayoutInflater.from(activity), null, false)
        binding.message = if (progress.loadingMessage.isNullOrEmpty()) R.string.app_in_request.getString() else progress.loadingMessage

        // 初始化 Dialog
        mDialog = Dialog(activity, R.style.app_progress_dialog)
        mDialog?.let {
            // 设置能否取消
            it.setCancelable(progress.cancelable)
            // 设置点击弹窗外不能取消
            it.setCanceledOnTouchOutside(false)
            // 设置弹窗布局
            it.setContentView(binding.root)
            // 显示
            it.show()
        }
    }

    private fun dismissDialog() {
        if (null == mDialog) {
            return
        }
        try {
            // 隐藏弹窗
            mDialog!!.dismiss()
            // 移除引用
            mDialog = null
        } catch (e: Exception) {
            LogUtils.e("ProgressDialogHelper", "dismissDialog : ${e.getStackTraceString()}")
        }
    }

    /**
     * 显示弹窗
     *
     * @param activity   Activity 对象
     * @param progress Progress 配置 Model
     */
    fun show(activity: Activity, progress: ProgressModel) {
        if (AppManager.getContext().isMainProcess) {
            // 主线程
            showDialog(activity, progress)
        } else {
            // 子线程
            MainThreadManager.postToMainThread(Runnable {
                showDialog(activity, progress)
            })
        }
    }

    /**
     * 隐藏弹窗
     */
    fun dismiss() {
        if (AppManager.getContext().isMainProcess) {
            // 主线程
            dismissDialog()
        } else {
            // 子线程
            runOnMainThread(Runnable {
                dismissDialog()
            })
        }
    }
}