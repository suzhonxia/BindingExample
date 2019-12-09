package com.sun.binding.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.blankj.utilcode.util.ScreenUtils
import com.sun.binding.R
import kotlin.math.min

/**
 * 先判断是否设定了mMaxHeight，如果设定了mMaxHeight，则直接使用mMaxHeight的值，
 * 如果没有设定mMaxHeight，则判断是否设定了mMaxRatio，如果设定了mMaxRatio的值 则使用此值与屏幕高度的乘积作为最高高度
 */
class MaxHeightLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    companion object {
        private const val DEFAULT_MAX_RATIO = 0.6f
        private const val DEFAULT_MAX_HEIGHT = 0f
    }

    private var mMaxRatio = DEFAULT_MAX_RATIO // 优先级高
    private var mMaxHeight = DEFAULT_MAX_HEIGHT // 优先级低

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightLayout)
        mMaxRatio = a.getFloat(R.styleable.MaxHeightLayout_mhl_HeightRatio, mMaxRatio)
        mMaxHeight = a.getDimension(R.styleable.MaxHeightLayout_mhl_HeightDimen, mMaxHeight)

        mMaxHeight = if (mMaxHeight <= 0) {
            mMaxRatio * ScreenUtils.getScreenHeight().toFloat()
        } else {
            min(mMaxHeight, mMaxRatio * ScreenUtils.getScreenHeight().toFloat())
        }
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight.toInt()
        }
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight.toInt()
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight.toInt()
        }
        val maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode)
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec)
    }
}