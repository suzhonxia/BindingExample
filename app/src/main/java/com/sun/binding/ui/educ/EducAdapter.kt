package com.sun.binding.ui.educ

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.entity.EducEntity
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.tool.getDrawable

/**
 * 亲职教育 Adapter
 */
class EducAdapter(data: MutableList<EducEntity>?) : BaseQuickAdapter<EducEntity, BaseViewHolder>(R.layout.educ_index_item, data) {
    override fun convert(helper: BaseViewHolder, item: EducEntity?) {
        GlideHelper.loadImage(helper.getView(R.id.ivBackdrop), item?.image, R.drawable.educ_index_item_backdrop.getDrawable())
        GlideHelper.loadImage(helper.getView(R.id.ivEducCover), item?.icon, R.drawable.app_placeholder_course.getDrawable())
        helper.setText(R.id.tvEducTitle, item?.name ?: "")
        helper.setText(R.id.tvEducDesc, item?.content ?: "")
    }
}