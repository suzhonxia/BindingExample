package com.sun.binding.model.educ

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.sun.binding.R
import com.sun.binding.constants.KeyConstant
import com.sun.binding.constants.SPLASH_DELAY_MS
import com.sun.binding.model.base.BaseRefreshViewModel
import com.sun.binding.model.base.RefreshConfig
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.net.repository.CourseRepository
import com.sun.binding.tools.ext.condition
import com.sun.binding.tools.tool.getColor
import com.sun.binding.tools.tool.getDrawable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    var ageSelector = SelectorEntity(name = BindingField("年龄"))

    /** 刷新配置 */
    override var refreshConfig: RefreshConfig = RefreshConfig(
        refreshEnable = BindingField(true), refreshing = BindingField(false), onRefresh = { getEducCourseData(true) },
        loadMoreEnable = BindingField(true), loadMore = BindingField(false), onLoadMore = { getEducCourseData(false) }
    )

    private fun getEducCourseData(isRefresh: Boolean) {
        viewModelScope.launch {
            delay(SPLASH_DELAY_MS)

            refreshConfig.finishEvent()
        }
    }
}

data class SelectorEntity(
    var selectedId: Int = -1,
    var name: BindingField<String> = BindingField(""),
    var clickAction: BindingField<() -> Unit> = BindingField(),
    var textColor: BindingField<Int> = BindingField(R.color.app_text_color_gray_light.getColor()),
    var drawable: BindingField<Drawable> = BindingField(R.drawable.course_icon_triangle_down.getDrawable())
) {

    // 是否展开
    var expanded: Boolean = false
        set(value) {
            val previousField = field
            if (value != previousField) {
                field = value
                update()
            }
        }

    // 是否高亮选中
    var highlight: Boolean = false
        set(value) {
            val previousField = field
            if (value != previousField) {
                field = value
                update()
            }
        }

    private fun update() {
        drawable.set(
            if (!expanded.condition && !highlight.condition) {
                R.drawable.course_icon_triangle_down.getDrawable()
            } else {
                if (expanded.condition) R.drawable.course_icon_triangle_up_select.getDrawable()
                else R.drawable.course_icon_triangle_down_select.getDrawable()
            }
        )
        textColor.set(
            if (!expanded.condition && !highlight.condition) {
                R.color.app_text_color_gray_light.getColor()
            } else {
                R.color.app_text_color_black_light.getColor()
            }
        )
    }
}