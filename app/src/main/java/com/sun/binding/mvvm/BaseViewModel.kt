package com.sun.binding.mvvm

import androidx.lifecycle.MutableLiveData
import com.sun.binding.mvvm.model.ProgressModel
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.mvvm.model.ToastModel
import com.sun.binding.mvvm.model.UiCloseModel
import org.koin.core.KoinComponent

abstract class BaseViewModel : BaseMvvmViewModel(), KoinComponent {
//    /** 弹窗显示数据  */
//    val showDialogData = MutableLiveData<GeneralDialog.Builder>()

    /** Snackbar 控制 */
    val snackbarData = MutableLiveData<SnackbarModel>()

    /** 控制进度条弹窗显示  */
    val progressData = MutableLiveData<ProgressModel>()

    /** 控制 UI 组件关闭 */
    val uiCloseData = MutableLiveData<UiCloseModel>()

    /** 控制 Toase 显示 */
    val toastData = MutableLiveData<ToastModel>()
}