package com.sun.binding.ui.base

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sun.binding.BR
import com.sun.binding.mvvm.BaseMvvmViewModel

/**
 * Activity 基类
 * - 添加了对 DataBinding 的支持
 * - [setContentView] 方法中处理了对 [mBinding] 的初始化
 *
 * @param VM MVVM ViewModel 类，继承 [BaseMvvmViewModel]
 * @param DB [ViewDataBinding] 对象
 */
abstract class BaseBindingActivity<VM : BaseMvvmViewModel, DB : ViewDataBinding> : BaseMvvmActivity<VM>() {

    /** DataBinding 对象 */
    protected lateinit var mBinding: DB

    override fun setContentView(layoutResID: Int) {
        // 初始化 DataBinding
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutResID, null, false)

        // 绑定 ViewModel
        mBinding.setVariable(BR.viewModel, viewModel)
        mBinding.executePendingBindings()

        // 设置布局
        super.setContentView(mBinding.root)
    }
}