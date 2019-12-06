@file:JvmName("ThrowableExt")

package com.sun.binding.tools.ext

import com.sun.binding.R
import com.sun.binding.tools.tool.getString
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * 获取堆栈跟踪字符串
 */
fun Throwable?.getStackTraceString(): String {
    return when (this) {
        is SocketTimeoutException -> R.string.app_net_error_timeout
        is ConnectException -> R.string.app_net_error_connect
        else -> R.string.app_net_error_failed
    }.getString()
}