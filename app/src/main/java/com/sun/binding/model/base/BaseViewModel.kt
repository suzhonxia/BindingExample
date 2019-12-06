package com.sun.binding.model.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sun.binding.model.base.task.TaskProxy
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.*
import com.sun.binding.net.NetCallback
import com.sun.binding.widget.state.StateEnum
import kotlinx.coroutines.*
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

    /** Task 代理实现 */
    private val taskProxy: TaskProxy = TaskProxy

    /** 状态值 */
    var viewState = BindingField(StateEnum.CONTENT)

    /** 重试 Action */
    open var retry = {}

    /**
     * @param tryBlock 尝试执行的挂起代码块
     * @param catchBlock 捕获异常的代码块 "协程对Retrofit的实现在失败、异常时没有onFailure的回调而是直接已Throwable的形式抛出"
     * @param finallyBlock finally代码块
     */
    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                catchBlock(e)
            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 在IO线程中开启
     */
    fun launchOnIO(callback: NetCallback.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val netCallback = NetCallback().also(callback)
            tryCatch(netCallback.tryBlock, netCallback.catchBlock, netCallback.finallyBlock)
        }
    }

    /**
     * 在主线程中开启
     */
    fun launchOnMain(callback: NetCallback.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            val netCallback = NetCallback().also(callback)
            tryCatch(netCallback.tryBlock, netCallback.catchBlock, netCallback.finallyBlock)
        }
    }

    /**
     * 开始定位
     */
    fun startLocation(context: Context, savable: Boolean = true, block: (() -> Unit)? = null) {
        taskProxy.startLocation(this, context, savable, block)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}