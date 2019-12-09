package com.sun.binding.model.educ

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sun.binding.constants.KeyConstant
import com.sun.binding.constants.SPLASH_DELAY_MS
import com.sun.binding.entity.EducOptionEntity
import com.sun.binding.entity.SelectorEntity
import com.sun.binding.model.base.BaseRefreshViewModel
import com.sun.binding.model.base.RefreshConfig
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.net.repository.CourseRepository
import kotlinx.coroutines.delay

class EducCourseViewModel(private val courseRepository: CourseRepository) : BaseRefreshViewModel() {

    /** id */
    private var educId = 0

    /** 显示标题 */
    var title = BindingField<CharSequence>("课程列表")

    /** 设置 Intent 数据 */
    fun setIntentData(bundle: Bundle?) {
        educId = bundle?.get(KeyConstant.KEY_ID) as? Int ?: educId
        title.set(bundle?.get(KeyConstant.KEY_TITLE) as? String ?: title.get())
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

    /** 查询默认的分类 */
    fun queryNormalCategory() = educOption.value?.mukuai?.firstOrNull { educId != 0 && educId == it.id }

    private fun getEducCourseData(isRefresh: Boolean) {
        launchOnMain {
            tryBlock {
                if (educOption.value == null) {
                    val educCourseOption = courseRepository.getEducCourseOption()
                    if (educCourseOption.checkResponseCode()) {
                        educOption.value = educCourseOption.data
                    }
                }

                delay(SPLASH_DELAY_MS)
                refreshConfig.finishEvent()
            }
            catchBlock {

            }
            finallyBlock {

            }
        }
    }
}