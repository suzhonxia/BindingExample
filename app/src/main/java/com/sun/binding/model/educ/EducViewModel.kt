package com.sun.binding.model.educ

import androidx.lifecycle.MutableLiveData
import com.sun.binding.entity.EducEntity
import com.sun.binding.mvvm.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.getStackTraceString

class EducViewModel(private val courseRepository: CourseRepository) : BaseViewModel() {

    /** 标记 - 是否正在刷新 */
    val refreshing = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { getEducData() }

    /** 列表数据 */
    val educCategoryList = MutableLiveData<List<EducEntity>>()

    /** 请求亲职教育一级列表数据 */
    private fun getEducData() {
        launchOnIO {
            tryBlock {
                val result = courseRepository.getEducIndex()
                if (result.checkResponseCode()) {
                    educCategoryList.postValue(result.data)
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