package com.sun.binding.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 * MVVM ViewModel 基类
 * - 继承 [ViewModel]
 * - 实现 [LifecycleObserver] 监听生命周期
 */
abstract class BaseMvvmViewModel : ViewModel()