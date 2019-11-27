package com.sun.binding.model.base

import com.sun.binding.constants.NET_PAGE_START

open class BaseRefreshViewModel : BaseViewModel() {
    /** 页码 */
    protected var pageFlag = NET_PAGE_START

    /**
     * 是否是刷新标记
     */
    fun isRefreshFlag(increment: Boolean = true): Boolean {
        val result = pageFlag == NET_PAGE_START
        if (increment) pageFlag++
        return result
    }
}