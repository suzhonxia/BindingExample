package com.sun.binding.net.repository

import com.sun.binding.net.WebService
import org.koin.core.KoinComponent

abstract class BaseRepository : KoinComponent {

    /** 网络请求服务 */
    protected abstract val mWebService: WebService
}