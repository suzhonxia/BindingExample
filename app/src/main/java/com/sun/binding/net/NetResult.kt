package com.sun.binding.net

import com.sun.binding.constants.NET_RESPONSE_CODE_SUCCESS
import com.sun.binding.constants.NET_RESPONSE_CODE_TOKEN_INVALID
import com.sun.binding.tools.ext.showToast

/**
 * 网络请求返回数据基本框架
 */
data class NetResult<T>
/**
 * 构造方法
 *
 * @param code 错误码
 * @param msg 错误信息
 * @param data 请求返回数据
 */
constructor(
    var code: Int = -1,
    var msg: String? = "",
    var data: T? = null
) {

    /**
     * 检查返回结果
     *
     * @param showMsgTip 是否进行默认的 Toast 提示
     * @return 请求是否成功
     */
    fun checkResponseCode(showMsgTip: Boolean = true): Boolean {
        if (code == NET_RESPONSE_CODE_SUCCESS) return true
        return if (handleErrorCode(code)) {
            false
        } else {
            if (showMsgTip) msg.orEmpty().showToast()
            false
        }
    }

    /**
     * 处理错误 code
     *
     * @return true 有异常，并处理了, false 没有异常
     */
    private fun handleErrorCode(code: Int): Boolean {
        // TODO:处理异常的返回 Code
        if (code == NET_RESPONSE_CODE_TOKEN_INVALID) {
            // Token 失效
            return true
        }
        return false
    }
}