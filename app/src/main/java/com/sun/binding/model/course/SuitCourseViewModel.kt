package com.sun.binding.model.course

import androidx.lifecycle.MutableLiveData
import com.sun.binding.entity.SuitCourseEntity
import com.sun.binding.model.base.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.toSnackbarMsg
import com.sun.binding.widget.state.StateEnum

class SuitCourseViewModel(private val courseRepository: CourseRepository) : BaseViewModel() {

    /** 标记 - 是否正在刷新 */
    val refreshing = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { getSuitCourse() }

    /** 列表数据 */
    val suitCourseList = MutableLiveData<List<SuitCourseEntity>>()

    override var retry = {
        viewState.set(StateEnum.LOADING)
        refreshing.set(true)
    }

    private fun getSuitCourse() {
        launchOnMain {
            tryBlock {
                val result = courseRepository.getSuitCourseData()
                if (result.checkResponseCode()) {
                    if (result.data.isNullOrEmpty()) {
                        viewState.set(StateEnum.EMPTY)
                    } else {
                        viewState.set(StateEnum.CONTENT)
                        suitCourseList.value = result.data
                    }
                }
            }
            catchBlock { e ->
                if (suitCourseList.value.isNullOrEmpty()) {
                    viewState.set(StateEnum.ERROR)
                } else {
                    snackbarData.value = e.getStackTraceString().toSnackbarMsg()
                }
            }
            finallyBlock {
                refreshing.set(false)
            }
        }
    }
}