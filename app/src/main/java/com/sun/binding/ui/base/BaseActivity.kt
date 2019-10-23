package com.sun.binding.ui.base

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.snackbar.Snackbar
import com.sun.binding.R
import com.sun.binding.mvvm.BaseViewModel
import com.sun.binding.tools.helper.ProgressDialogHelper

/**
 * Activity 基类
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseBindingActivity<VM, DB>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    override fun onPause() {
        super.onPause()
        ProgressDialogHelper.dismiss()
    }

    override fun getResources(): Resources {
        // 禁止App 字体大小随系统字体大小调节
        val resources = super.getResources()
        if (resources != null && resources.configuration.fontScale != 1.0F) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0F
            createConfigurationContext(configuration)
        }
        return resources
    }

    override fun startAnim() {
        overridePendingTransition(R.anim.app_anim_left_in, R.anim.app_anim_right_out)
    }

    override fun finishAnim() {
        overridePendingTransition(R.anim.app_anim_right_in, R.anim.app_anim_left_out)
    }

    /**
     * 添加观察者
     */
    private fun observeData() {
        viewModel.snackbarData.observe(this, Observer {
            if (it.content.isNullOrBlank()) {
                return@Observer
            }
            val snackBar = Snackbar.make(mBinding.root, it.content.orEmpty(), it.duration)
            snackBar.setTextColor(it.contentColor)
            snackBar.setBackgroundTint(it.contentBgColor)
            if (it.actionText != null && it.onAction != null) {
                snackBar.setAction(it.actionText, it.onAction)
                snackBar.setActionTextColor(it.actionColor)
            }
            if (it.onCallback != null) {
                snackBar.addCallback(it.onCallback)
            }
            snackBar.show()
        })
        viewModel.progressData.observe(this, Observer { progress ->
            if (progress == null || progress.show) {
                ProgressDialogHelper.dismiss()
            } else {
                ProgressDialogHelper.show(mContext, progress.cancelable)
            }
        })
        viewModel.uiCloseData.observe(this, Observer { close ->
            close?.let { model ->
                setResult(model.resultCode, model.result)
                finish()
            }
        })
        viewModel.toastData.observe(this, Observer { toast ->
            toast?.let { model ->
                if (model.duration == Toast.LENGTH_SHORT) {
                    ToastUtils.showShort(model.msg)
                } else if (model.duration == Toast.LENGTH_LONG) {
                    ToastUtils.showLong(model.msg)
                }
            }
        })
    }
}