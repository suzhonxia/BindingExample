package com.sun.binding.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.Utils
import com.hjq.bar.SelectorDrawable
import com.hjq.bar.style.BaseTitleBarStyle
import com.sun.binding.R
import com.sun.binding.tools.tool.getColor
import com.sun.binding.tools.tool.getDrawable

class TitleBarStyleImpl(context: Context) : BaseTitleBarStyle(context) {

    override fun getBackground(): Drawable? = R.color.app_white.getDrawable()

    override fun getTitleBarHeight(): Int = Utils.getApp().resources.getDimensionPixelSize(R.dimen.dp_52)

    override fun getTitleColor(): Int = R.color.app_text_color_black.getColor()

    override fun getTitleSize(): Float = Utils.getApp().resources.getDimension(R.dimen.sp_18)

    override fun getBackIcon(): Drawable? = R.drawable.app_icon_back_dark.getDrawable()

    override fun getLeftColor(): Int = R.color.app_text_color_black_light

    override fun getLeftSize(): Float = Utils.getApp().resources.getDimension(R.dimen.dp_24)

    override fun getLeftBackground(): Drawable =
        SelectorDrawable.Builder()
            .setDefault(ColorDrawable(0x00000000))
            .setFocused(ColorDrawable(0x0C000000))
            .setPressed(ColorDrawable(0x0C000000))
            .builder()

    override fun getRightColor(): Int = R.color.app_text_color_black_light

    override fun getRightSize(): Float = Utils.getApp().resources.getDimension(R.dimen.dp_24)

    override fun getRightBackground(): Drawable = leftBackground

    override fun isLineVisible(): Boolean = true

    override fun getLineSize(): Int = Utils.getApp().resources.getDimensionPixelSize(R.dimen.dp_line)

    override fun getLineDrawable(): Drawable? = R.color.app_dividing_bg.getDrawable()
}