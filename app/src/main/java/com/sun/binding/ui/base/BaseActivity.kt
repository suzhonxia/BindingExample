package com.sun.binding.ui.base

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.sun.binding.R
import com.sun.binding.model.base.BaseViewModel
import com.sun.binding.tools.helper.ProgressDialogHelper
import com.sun.binding.tools.helper.SnackbarHelper
import com.sun.binding.tools.helper.XPopupDialogHelper

/**
 * Activity 基类
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseBindingActivity<VM, DB>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
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
        viewModel.dialogData.observe(this, Observer { dialog ->
            if (dialog == null || !dialog.show) {
                XPopupDialogHelper.dismiss(dialog)
            } else {
                XPopupDialogHelper.show(mContext, dialog)
            }
        })
        viewModel.snackbarData.observe(this, Observer {
            SnackbarHelper.show(mBinding.root, it)
        })
        viewModel.progressData.observe(this, Observer { progress ->
            if (progress == null || progress.show) {
                ProgressDialogHelper.dismiss()
            } else {
                ProgressDialogHelper.show(mContext, progress)
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