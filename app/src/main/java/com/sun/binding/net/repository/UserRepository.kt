package com.sun.binding.net.repository

import com.sun.binding.net.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * 用户相关数据仓库
 */
class UserRepository : KoinComponent {

    /** 网络请求服务 */
    private val mWebService: WebService by inject()

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo() = withContext(Dispatchers.IO) {
        mWebService.getUserInfo()
    }
}