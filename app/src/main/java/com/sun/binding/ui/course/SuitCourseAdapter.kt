package com.sun.binding.ui.course

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.entity.SuitCourseEntity

class SuitCourseAdapter(data: List<SuitCourseEntity>?) : BaseQuickAdapter<SuitCourseEntity, BaseViewHolder>(R.layout.course_suit_index_item, data) {
    override fun convert(helper: BaseViewHolder, item: SuitCourseEntity?) {
        helper.setText(R.id.tvSuitName, item?.name)
    }
}