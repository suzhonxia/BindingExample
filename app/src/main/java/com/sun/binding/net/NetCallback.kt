package com.sun.binding.net

import kotlinx.coroutines.CoroutineScope

/**
 * 网络请求 callback
 */
class NetCallback {

    internal var tryBlock: suspend CoroutineScope.() -> Unit = {}
    internal var catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit = {}
    internal var finallyBlock: suspend CoroutineScope.() -> Unit = {}

    fun tryBlock(tryBlock: suspend CoroutineScope.() -> Unit) {
        this.tryBlock = tryBlock
    }

    fun catchBlock(catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit) {
        this.catchBlock = catchBlock
    }

    fun finallyBlock(finallyBlock: suspend CoroutineScope.() -> Unit) {
        this.finallyBlock = finallyBlock
    }
}