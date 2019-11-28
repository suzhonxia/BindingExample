package com.sun.binding.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * 嵌套的 GridView
 */
class NestedGridView : GridView {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
        val params = layoutParams
        params.height = measuredHeight
    }
}