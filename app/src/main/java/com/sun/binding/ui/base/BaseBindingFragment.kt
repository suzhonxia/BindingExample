package com.sun.binding.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sun.binding.BR
import com.sun.binding.model.base.BaseMvvmViewModel

/**
 * Fragment 基类
 * - 添加了对 DataBinding 的支持
 * - [onCreateView] 方法中处理了对 [mBinding] 的初始化
 *
 * @param VM MVVM ViewModel 类，继承 [BaseMvvmViewModel]
 * @param DB [ViewDataBinding] 对象
 */
abstract class BaseBindingFragment<VM : BaseMvvmViewModel, DB : ViewDataBinding> : BaseMvvmFragment<VM>() {

    /** DataBinding 对象 */
    protected lateinit var mBinding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            // 初始化 DataBinding
            mBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

            // 绑定 ViewModel
            mBinding.setVariable(BR.viewModel, viewModel)
            mBinding.executePendingBindings()

            rootView = mBinding.root
        } else {
            (rootView?.parent as? ViewGroup?)?.removeView(rootView)
        }
        return rootView
    }
}