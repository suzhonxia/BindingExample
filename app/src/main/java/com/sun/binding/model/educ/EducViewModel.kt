package com.sun.binding.model.educ

import androidx.lifecycle.MutableLiveData
import com.sun.binding.entity.EducEntity
import com.sun.binding.model.base.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.toSnackbarMsg
import com.sun.binding.widget.state.StateEnum

class EducViewModel(private val courseRepository: CourseRepository) : BaseViewModel() {

    /** 标记 - 是否正在刷新 */
    val refreshing = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { getEducData() }

    /** 列表数据 */
    val educCategoryList = MutableLiveData<List<EducEntity>>()

    override var retry = {
        viewState.set(StateEnum.LOADING)
        refreshing.set(true)
    }

    /** 请求亲职教育一级列表数据 */
    private fun getEducData() {
        launchOnMain {
            tryBlock {
                val result = courseRepository.getEducIndex()
                if (result.checkResponseCode()) {
                    if (result.data.isNullOrEmpty()) {
                        viewState.set(StateEnum.EMPTY)
                    } else {
                        viewState.set(StateEnum.CONTENT)
                        educCategoryList.value = result.data
                    }
                }
            }
            catchBlock { e ->
                if (educCategoryList.value.isNullOrEmpty()) {
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