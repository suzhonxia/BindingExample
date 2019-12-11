package com.sun.binding.model.circle

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sun.binding.constants.CircleTab
import com.sun.binding.constants.KeyConstant
import com.sun.binding.constants.NET_PAGE_START
import com.sun.binding.entity.ProductEntity
import com.sun.binding.model.base.BaseRefreshViewModel
import com.sun.binding.model.base.RefreshConfig
import com.sun.binding.model.base.task.LocationTarget
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.toSnackbarMsg
import com.sun.binding.tools.util.event.Event
import com.sun.binding.widget.state.StateEnum

class CircleProductViewModel(private val courseRepository: CourseRepository) : BaseRefreshViewModel(), LocationTarget {

    /** 类型 */
    var type = CircleTab.TAB_CIRCLE_ALL

    /** 获取 Intent 数据 */
    override fun obtainIntentData(bundle: Bundle?) {
        type = bundle?.get(KeyConstant.KEY_TYPE) as? Int ?: 0
    }

    /** 是否需要定位 */
    fun needLocation() = type == CircleTab.TAB_CIRCLE_NEARBY

    /** 刷新配置 */
    override var refreshConfig: RefreshConfig = RefreshConfig(
        refreshEnable = BindingField(true), refreshing = BindingField(false), onRefresh = { getCircleProductData(true) },
        loadMoreEnable = BindingField(true), loadMore = BindingField(false), onLoadMore = { getCircleProductData(false) }
    )

    /** 作品列表数据 */
    val circleProductList = MutableLiveData<List<ProductEntity>>()

    /** 重试 or 去开启 */
    override var retry = {
        viewState.set(StateEnum.LOADING)
        retryTarget.postValue(Event(Unit))
    }

    /** 请求同学圈作品列表数据 */
    private fun getCircleProductData(isRefresh: Boolean) {
        if (isRefresh) pageFlag = NET_PAGE_START
        launchOnMain {
            tryBlock {
                val result = courseRepository.getCircleProductData(type, pageFlag)
                if (result.checkResponseCode()) {
                    val course = result.data.course
                    if (course.isNullOrEmpty()) {
                        if (circleProductList.value.isNullOrEmpty()) {
                            viewState.set(StateEnum.EMPTY)
                        } else {
                            refreshConfig.noMore.set(true)
                        }
                    } else {
                        refreshConfig.noMore.set(false)
                        viewState.set(StateEnum.CONTENT)
                        circleProductList.postValue(course)
                    }
                }
            }
            catchBlock { e ->
                if (circleProductList.value.isNullOrEmpty()) {
                    viewState.set(StateEnum.ERROR)
                } else {
                    snackbarData.value = e.getStackTraceString().toSnackbarMsg()
                }
            }
            finallyBlock {
                refreshConfig.finishEvent()
            }
        }
    }
}