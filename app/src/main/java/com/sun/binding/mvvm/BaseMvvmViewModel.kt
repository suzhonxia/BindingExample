package com.sun.binding.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.sun.binding.ui.base.Tagable
import java.util.*

/**
 * MVVM ViewModel 基类
 * - 继承 [ViewModel]
 * - 实现 [LifecycleObserver] 监听生命周期
 */
abstract class BaseMvvmViewModel : ViewModel(), Tagable {
    override val mTagMaps: HashMap<String, Any> = hashMapOf()

    override var mClosed: Boolean = false

    override fun onCleared() {
        clearTags()
        LogUtils.i("BaseMvvmViewModel", "View onCleared ----> ViewModel: $this")
    }
}