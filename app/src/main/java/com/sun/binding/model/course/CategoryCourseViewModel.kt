package com.sun.binding.model.course

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.sun.binding.constants.KeyConstant.KEY_ID
import com.sun.binding.constants.NET_PAGE_START
import com.sun.binding.entity.CategoryOptionEntity
import com.sun.binding.entity.CourseEntity
import com.sun.binding.entity.SelectorEntity
import com.sun.binding.model.base.BaseRefreshViewModel
import com.sun.binding.model.base.RefreshConfig
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.toSnackbarMsg
import com.sun.binding.widget.state.StateEnum
import java.lang.Exception

class CategoryCourseViewModel(private val courseRepository: CourseRepository) : BaseRefreshViewModel() {

    /** 分类 id */
    private var categoryId: Int = 0

    override fun obtainIntentData(bundle: Bundle?) {
        categoryId = bundle?.getInt(KEY_ID) ?: 0
    }

    /** 排序 */
    var orderSelector = SelectorEntity(name = BindingField("排序"))

    /** 分类 */
    var categorySelector = SelectorEntity(name = BindingField("分类"))

    /** 筛选 */
    var filterSelector = SelectorEntity(name = BindingField("筛选"))

    /** 刷新配置 */
    override var refreshConfig: RefreshConfig = RefreshConfig(
        refreshEnable = BindingField(true), refreshing = BindingField(false), onRefresh = { getCategoryCourseData(true) },
        loadMoreEnable = BindingField(true), loadMore = BindingField(false), onLoadMore = { getCategoryCourseData(false) }
    )

    /** 筛选数据 */
    var categoryOption = MutableLiveData<CategoryOptionEntity>()

    /** 列表数据 */
    val categoryCourseList = MutableLiveData<List<CourseEntity>>()

    /** 重试 */
    override var retry = {
        viewState.set(StateEnum.LOADING)
        refreshConfig.refreshing.set(true)
    }

    /** 查询默认的分类 */
    fun queryNormalCategory() = categoryOption.value?.getCategoryList()?.firstOrNull { categoryId == it.id }

    // 1. 请求课程筛选项数据
    fun getEducCourseOption() {
        launchOnMain {
            tryBlock {
                val categoryCourseOption = courseRepository.getCategoryCourseOption()
                if (categoryCourseOption.checkResponseCode()) {
                    categoryOption.value = categoryCourseOption.data
                }
            }
            catchBlock { e ->
                refreshConfig.finishEvent()
                if (categoryCourseList.value.isNullOrEmpty()) {
                    viewState.set(StateEnum.ERROR)
                } else {
                    snackbarData.value = e.getStackTraceString().toSnackbarMsg()
                }
            }
        }
    }

    // 请求分类课程列表
    private fun getCategoryCourseData(isRefresh: Boolean) {
        if (isRefresh) pageFlag = NET_PAGE_START
        launchOnMain {
            tryBlock {
                val courseData = courseRepository.getCategoryCourseData(generateCourseSelectOption(), pageFlag)
                if (courseData.checkResponseCode()) {
                    if (courseData.data.isNullOrEmpty()) {
                        if (isRefreshFlag(false)) {
                            viewState.set(StateEnum.EMPTY)
                        } else {
                            refreshConfig.noMore.set(true)
                        }
                    } else {
                        refreshConfig.noMore.set(false)
                        viewState.set(StateEnum.CONTENT)
                        categoryCourseList.value = courseData.data
                    }
                }
            }
            catchBlock { e ->
                if (categoryCourseList.value.isNullOrEmpty()) {
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

    // 生成排序、分类、筛选组合请求参
    private fun generateCourseSelectOption(): String {
        val data = mutableMapOf<String, Any>()
        data["order"] = orderSelector.selectedData
        data["cid"] = categorySelector.selectedId
        if (filterSelector.selectedData.isNotEmpty()) {
            try {
                data.putAll(GsonUtils.fromJson(filterSelector.selectedData, GsonUtils.getMapType(String::class.java, GsonUtils.getArrayType(String::class.java))))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return GsonUtils.toJson(data)
    }
}