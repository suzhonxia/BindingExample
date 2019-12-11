package com.sun.binding.model.educ

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sun.binding.constants.KeyConstant
import com.sun.binding.constants.NET_PAGE_START
import com.sun.binding.entity.CourseEntity
import com.sun.binding.entity.EducOptionEntity
import com.sun.binding.entity.SelectorEntity
import com.sun.binding.model.base.BaseRefreshViewModel
import com.sun.binding.model.base.RefreshConfig
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.toSnackbarMsg
import com.sun.binding.tools.util.event.Event
import com.sun.binding.widget.state.StateEnum

class EducCourseViewModel(private val courseRepository: CourseRepository) : BaseRefreshViewModel() {

    /** id */
    private var educId = 0

    /** 获取 Intent 数据 */
    override fun obtainIntentData(bundle: Bundle?) {
        educId = bundle?.get(KeyConstant.KEY_ID) as? Int ?: educId
    }

    /** 排序 */
    var sortSelector = SelectorEntity(name = BindingField("排序"))

    /** 分类 */
    var categorySelector = SelectorEntity(name = BindingField("分类"))

    /** 年龄 */
    var ageSelector = SelectorEntity(name = BindingField("年龄段"))

    /** 刷新配置 */
    override var refreshConfig: RefreshConfig = RefreshConfig(
        refreshEnable = BindingField(true), refreshing = BindingField(false), onRefresh = { getEducCourseData(true) },
        loadMoreEnable = BindingField(true), loadMore = BindingField(false), onLoadMore = { getEducCourseData(false) }
    )

    /** 筛选数据 */
    var educOption = MutableLiveData<EducOptionEntity>()

    /** 列表数据 */
    val educCourseList = MutableLiveData<List<CourseEntity>>()

    /** 重试 retry */
    val retryTarget = MutableLiveData<Event<Unit>>()

    /** 重试 */
    override var retry = {
        viewState.set(StateEnum.LOADING)
        retryTarget.postValue(Event(Unit))
    }

    /** 查询默认的分类 */
    fun queryNormalCategory() = educOption.value?.mukuai?.firstOrNull { educId != 0 && educId == it.id }

    // 1. 请求课程筛选项数据
    fun getEducCourseOption() {
        launchOnMain {
            tryBlock {
                val educCourseOption = courseRepository.getEducCourseOption()
                if (educCourseOption.checkResponseCode()) {
                    educOption.value = educCourseOption.data
                }
            }
            catchBlock { e ->
                refreshConfig.finishEvent()
                if (educCourseList.value.isNullOrEmpty()) {
                    viewState.set(StateEnum.ERROR)
                } else {
                    snackbarData.value = e.getStackTraceString().toSnackbarMsg()
                }
            }
        }
    }

    // 3. 请求课程列表
    private fun getEducCourseData(isRefresh: Boolean) {
        if (isRefresh) pageFlag = NET_PAGE_START
        launchOnMain {
            tryBlock {
                val courseData = courseRepository.getEducCourseData(sortSelector.selectedId, categorySelector.selectedId, ageSelector.selectedId, pageFlag)
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
                        educCourseList.value = courseData.data
                    }
                }
            }
            catchBlock { e ->
                if (educCourseList.value.isNullOrEmpty()) {
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