package com.sun.binding.widget

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView

/**
 * 通用的 Indicator 指示器标题
 */
class CommonPagerTitleView(context: Context?) : TextView(context), IMeasurablePagerTitleView {
    var selectedColor = 0
    var selectedTextSize = SizeUtils.sp2px(16F)

    var normalColor = 0
    var normalTextSize = SizeUtils.sp2px(16F)

    init {
        init()
    }

    private fun init() {
        val padding = SizeUtils.dp2px(10F)
        apply {
            setSingleLine()
            gravity = Gravity.CENTER
            ellipsize = TextUtils.TruncateAt.END
            setPadding(padding, 0, padding, 0)
        }
    }

    override fun getContentLeft(): Int {
        val bound = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, bound)
        val contentWidth = bound.width()
        return left + width / 2 - contentWidth / 2
    }

    override fun getContentTop(): Int {
        val metrics = paint.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 - contentHeight / 2).toInt()
    }

    override fun getContentRight(): Int {
        val bound = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, bound)
        val contentWidth = bound.width()
        return left + width / 2 + contentWidth / 2
    }

    override fun getContentBottom(): Int {
        val metrics = paint.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 + contentHeight / 2).toInt()
    }

    override fun onSelected(index: Int, totalCount: Int) {
        setTextColor(selectedColor)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTextSize.toFloat())
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        setTextColor(normalColor)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, normalTextSize.toFloat())
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {

    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {

    }
}