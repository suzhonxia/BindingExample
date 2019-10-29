package com.sun.binding.net.repository

import com.sun.binding.net.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject

/**
 * 首页相关数据仓库
 */
class HomeRepository : BaseRepository() {

    override val mWebService: WebService by inject()

    /**
     * 获取用户信息
     */
    suspend fun getHomeData() = withContext(Dispatchers.IO) {
        mWebService.getHomeData()
    }
}