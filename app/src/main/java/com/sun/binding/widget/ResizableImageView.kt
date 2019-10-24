package com.sun.binding.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil

/**
 * 宽度充满屏幕，高度自适应的 ImageView
 */
class ResizableImageView : AppCompatImageView {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (drawable != null) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            // 高度根据使用的图片的宽度充满屏幕计算而得到
            val height = ceil((width * drawable.intrinsicHeight / drawable.intrinsicWidth).toDouble()).toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}