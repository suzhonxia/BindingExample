package com.sun.binding.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.sun.binding.model.base.BaseViewModel
import com.sun.binding.tools.helper.ProgressDialogHelper
import com.sun.binding.tools.helper.SnackbarHelper
import com.sun.binding.tools.helper.XPopupDialogHelper

/**
 * Fragment 基类
 * - 添加 [lazyInitView] 方法进行页面懒加载
 * - 维护 [isSwiftLoad] : 是否开启懒加载
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseBindingFragment<VM, DB>() {

    /** 数据是否初始化 */
    private var isInitData: Boolean = false

    /** Fragment 是否可见 */
    private var isVisibleToUser: Boolean = false

    /** View 是否加载完成 */
    private var isPrepareView: Boolean = false

    /** 是否直接加载(不使用懒加载) */
    protected var isSwiftLoad: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepareView = true
        if (isSwiftLoad) {
            isVisibleToUser = true

            // 初始化布局
            lazyInitView()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lazyInitView()
    }

    override fun onResume() {
        super.onResume()
        isVisibleToUser = true
        lazyInitView()
    }

    override fun onPause() {
        super.onPause()
        isVisibleToUser = false
        lazyInitView()
    }

    /**
     * 懒加载方法
     */
    private fun lazyInitView() {
        if (!isInitData && isVisibleToUser && isPrepareView) {
            isInitData = true
            initView()
        }
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
            if (progress == null || !progress.show) {
                ProgressDialogHelper.dismiss()
            } else {
                ProgressDialogHelper.show(mContext, progress)
            }
        })
        viewModel.uiCloseData.observe(this, Observer { close ->
            close?.let { model ->
                mContext.setResult(model.resultCode, model.result)
                mContext.finish()
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