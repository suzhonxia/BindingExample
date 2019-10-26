package com.sun.binding.mvvm

import androidx.lifecycle.MutableLiveData
import com.lxj.xpopup.XPopup
import com.sun.binding.mvvm.model.*
import org.koin.core.KoinComponent

abstract class BaseViewModel : BaseMvvmViewModel(), KoinComponent {
    /** 弹窗显示数据  */
    val dialogData = MutableLiveData<DialogModel>()

    /** Snackbar 控制 */
    val snackbarData = MutableLiveData<SnackbarModel>()

    /** 控制进度条弹窗显示  */
    val progressData = MutableLiveData<ProgressModel>()

    /** 控制 UI 组件关闭 */
    val uiCloseData = MutableLiveData<UiCloseModel>()

    /** 控制 Toast 显示 */
    val toastData = MutableLiveData<ToastModel>()
}