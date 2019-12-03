package com.sun.binding.model.course

import androidx.lifecycle.MutableLiveData
import com.sun.binding.entity.SuitCourseEntity
import com.sun.binding.model.base.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString

class SuitCourseViewModel(private val courseRepository: CourseRepository) : BaseViewModel() {

    /** 标记 - 是否正在刷新 */
    val refreshing = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { getSuitCourse() }

    /** 列表数据 */
    val suitCourseList = MutableLiveData<List<SuitCourseEntity>>()

    private fun getSuitCourse() {
        launchOnIO {
            tryBlock {
                val result = courseRepository.getSuitCourseData()
                if (result.checkResponseCode()) {
                    suitCourseList.postValue(result.data)
                }
            }
            catchBlock { e ->
                snackbarData.postValue(SnackbarModel(e.getStackTraceString()))
            }
            finallyBlock {
                refreshing.set(false)
            }
        }
    }
}