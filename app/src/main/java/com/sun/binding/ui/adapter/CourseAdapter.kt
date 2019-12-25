package com.sun.binding.ui.adapter

import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.entity.CourseConfigEntity
import com.sun.binding.entity.CourseEntity
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.tool.getDrawable

/**
 * 通用的课程列表 Adapter
 */
class CourseAdapter constructor(
    data: MutableList<CourseEntity>?, private val courseConfig: CourseConfigEntity
) : BaseQuickAdapter<CourseEntity, BaseViewHolder>(R.layout.course_index_item, data) {
    override fun convert(helper: BaseViewHolder, item: CourseEntity?) {
        GlideHelper.loadImage(helper.getView(R.id.ivCourseCover), item?.image, R.drawable.app_placeholder_course.getDrawable())
        helper.setText(R.id.tvCourseName, item?.name)
        helper.setText(R.id.tvCoursePieces, if (item?.part_num ?: 0 > 0) "${item?.part_num}PCS" else "")
        helper.setText(R.id.tvCourseLearned, "${item?.readnum}人学习过")

        helper.setGone(R.id.tvCoursePieces, courseConfig.hasPart)
        if (!courseConfig.singleLine) {
            val tvCourseName = helper.getView<TextView>(R.id.tvCourseName)
            tvCourseName.isSingleLine = false
            tvCourseName.maxLines = 2
            tvCourseName.minHeight = SizeUtils.dp2px(38F)
        }
    }
}