package com.sun.binding.model.circle

import androidx.lifecycle.MutableLiveData
import com.sun.binding.constants.NET_PAGE_START
import com.sun.binding.entity.ProductEntity
import com.sun.binding.model.base.BaseRefreshViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString

class CircleProductViewModel(private val courseRepository: CourseRepository) : BaseRefreshViewModel() {

    /** 类型 */
    var type = 0

    /** 标记 - 是否正在刷新 */
    val refreshing = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { getCircleProductData(true) }

    /** 标记 - 是否正在加载更多 */
    val loadMore = BindingField(false)

    /** 加载更多回调 */
    val onLoadMore: () -> Unit = { getCircleProductData(false) }

    /** 作品列表数据 */
    val circleProductList = MutableLiveData<List<ProductEntity>>()

    /** 请求同学圈作品列表数据 */
    private fun getCircleProductData(isRefresh: Boolean) {
        if (isRefresh) pageFlag = NET_PAGE_START
        launchOnIO {
            tryBlock {
                val result = courseRepository.getCircleProductData(type, pageFlag)
                if (result.checkResponseCode()) {
                    circleProductList.postValue(result.data.course)
                }
            }
            catchBlock { e ->
                snackbarData.postValue(SnackbarModel(e.getStackTraceString()))
            }
            finallyBlock {
                refreshing.set(false)
                loadMore.set(false)
            }
        }
    }
}