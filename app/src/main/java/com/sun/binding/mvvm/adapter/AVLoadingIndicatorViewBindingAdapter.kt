@file:Suppress("unused")

package com.sun.binding.mvvm.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.wang.avi.AVLoadingIndicatorView

/**
 * AVLoadingIndicatorView DataBinding 扩展属性
 */

/**
 * 设置 Loading 动画开关
 *
 * @param loading [AVLoadingIndicatorView] 对象
 * @param loadingToggle 启用或禁用 Loading 动画
 */
@BindingAdapter("android:bind_loading_toggle")
fun setNavigationAnimation(loading: AVLoadingIndicatorView, loadingToggle: Boolean?) {
    loadingToggle?.let {
        if (it && loading.visibility != View.VISIBLE) {
            loading.smoothToShow()
        } else if (!it && loading.visibility == View.VISIBLE) {
            loading.smoothToHide()
        }
    }
}