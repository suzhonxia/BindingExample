package com.sun.binding.net

import com.sun.binding.constants.NET_RESPONSE_CODE_SUCCESS
import com.sun.binding.mvvm.model.SnackbarModel

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
     * @return 请求是否成功
     */
    fun success(): Boolean {
        // TODO:处理异常的返回 Code
//        if (code == NET_RESPONSE_CODE_LOGIN_FAILED) {
//            // 登录失败，需要重新登录
//            LoginActivity.actionStart(fromNet = true)
//        }
        return code == NET_RESPONSE_CODE_SUCCESS
    }

    fun toError(): SnackbarModel {
        return SnackbarModel(msg.orEmpty())
    }
}