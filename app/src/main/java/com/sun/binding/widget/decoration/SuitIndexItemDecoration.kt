package com.sun.binding.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

class SuitIndexItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)

        outRect.left = SizeUtils.dp2px(15F)
        outRect.right = SizeUtils.dp2px(15F)
        outRect.bottom = SizeUtils.dp2px(15F)
        if (position == 0) { outRect.top = SizeUtils.dp2px(15F) }
    }
}