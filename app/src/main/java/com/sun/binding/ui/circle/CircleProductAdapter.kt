package com.sun.binding.ui.circle

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.entity.ProductEntity
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.tool.getDrawable

class CircleProductAdapter(data: List<ProductEntity>?) : BaseQuickAdapter<ProductEntity, BaseViewHolder>(R.layout.circle_product_item, data) {
    override fun convert(helper: BaseViewHolder, item: ProductEntity?) {
        GlideHelper.loadImage(helper.getView(R.id.ivAuthorAvatar), item?.avatar, R.drawable.app_user_default_avatar.getDrawable())
        helper.setText(R.id.tvAuthorName, item?.nickname)
        helper.setText(R.id.tvCreateTime, item?.createtime)
        helper.setText(R.id.tvFocusAction, if (item?.focus == 0) "+ 关注" else "已关注")
        helper.getView<View>(R.id.tvFocusAction).isSelected = item?.focus == 1
        helper.setText(R.id.tvProductDistance, item?.distance)
        helper.setGone(R.id.tvProductDistance, !item?.distance.isNullOrEmpty())
        helper.setText(R.id.tvProductFavour, item?.favour)
        helper.getView<TextView>(R.id.tvProductFavour).setCompoundDrawablesWithIntrinsicBounds(
            (if (item?.favourType == 0) R.drawable.app_icon_favour else R.drawable.app_icon_favour_sel).getDrawable(), null, null, null
        )
    }
}