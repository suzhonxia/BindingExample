package com.sun.binding.entity

import android.graphics.drawable.Drawable
import com.sun.binding.R
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.tools.ext.condition
import com.sun.binding.tools.tool.getColor
import com.sun.binding.tools.tool.getDrawable

data class SelectorEntity(
    var selectedId: Int = 0,
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

    fun updateStyleAndData(highlight: Boolean, name: String, selectedId: Int) {
        this.highlight = highlight
        this.name.set(name)
        this.selectedId = selectedId
    }
}