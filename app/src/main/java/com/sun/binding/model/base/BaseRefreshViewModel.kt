package com.sun.binding.model.base

import com.sun.binding.constants.NET_PAGE_START
import com.sun.binding.mvvm.binding.BindingField

open class BaseRefreshViewModel : BaseViewModel() {
    /** 页码 */
    protected var pageFlag = NET_PAGE_START

    /** 刷新配置 */
    open var refreshConfig = RefreshConfig()

    /**
     * 是否是刷新标记
     */
    fun isRefreshFlag(increment: Boolean = true): Boolean {
        val result = pageFlag == NET_PAGE_START
        if (increment) pageFlag++
        return result
    }
}

/**
 * 刷新配置项
 */
data class RefreshConfig(
    /** 是否启用刷新 */
    var refreshEnable: BindingField<Boolean> = BindingField(false),
    /** 标记 - 是否正在刷新 */
    var refreshing: BindingField<Boolean> = BindingField(false),
    /** 刷新回调 */
    var onRefresh: (() -> Unit)? = null,

    /** 是否启用加载更多 */
    var loadMoreEnable: BindingField<Boolean> = BindingField(false),
    /** 标记 - 是否正在加载更多 */
    var loadMore: BindingField<Boolean> = BindingField(false),
    /** 加载更多回调 */
    var onLoadMore: (() -> Unit)? = null,

    /** 没有更多了 */
    var noMore: BindingField<Boolean> = BindingField(false)
) {

    /**
     * 结束事件(刷新 or 加载更多)
     */
    fun finishEvent() {
        refreshing.set(false)
        loadMore.set(false)
    }
}