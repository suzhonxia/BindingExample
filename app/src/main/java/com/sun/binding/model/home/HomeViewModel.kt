package com.sun.binding.model.home

import com.sun.binding.mvvm.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField

class HomeViewModel : BaseViewModel() {

    /** 标记 - 是否正在刷新 */
    val refreshing: BindingField<Boolean> = BindingField(false)

    /** 标记 - 是否正在加载更多 */
    val loadMore: BindingField<Boolean> = BindingField(false)

    fun onResume() {
    }

    fun onPause() {
    }
}