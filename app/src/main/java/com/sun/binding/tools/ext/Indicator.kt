@file:JvmName("IndicatorExt")

package com.sun.binding.tools.ext

import android.content.Context
import android.graphics.Typeface
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.sun.binding.R
import com.sun.binding.widget.CommonPagerTitleView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * MagicIndicator 和 ViewPager 绑定
 */
fun MagicIndicator.bindViewPager(context: Context, fm: FragmentManager, vp: ViewPager, tabNames: List<String>, fragments: List<Fragment>) {
    this.navigator = CommonNavigator(context).apply {
        isAdjustMode = true
        adapter = object : CommonNavigatorAdapter() {
            override fun getCount() = tabNames.size

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView = CommonPagerTitleView(context).apply {
                text = tabNames[index]
                normalTextSize = SizeUtils.sp2px(15F)
                normalColor = ColorUtils.getColor(R.color.app_text_color_gray_light)
                selectedTextSize = SizeUtils.sp2px(16F)
                selectedColor = ColorUtils.getColor(R.color.app_text_color_black_light)
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                setOnClickListener { vp.currentItem = index }
            }

            override fun getIndicator(context: Context?): IPagerIndicator = LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                yOffset = SizeUtils.dp2px(6F).toFloat()
                lineWidth = SizeUtils.dp2px(15F).toFloat()
                lineHeight = SizeUtils.dp2px(2F).toFloat()
                roundRadius = SizeUtils.dp2px(2F).toFloat()
                setColors(ColorUtils.getColor(R.color.app_text_color_black_light))
            }
        }
    }
    ViewPagerHelper.bind(this, vp)
    vp.offscreenPageLimit = fragments.size
    vp.adapter = object : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = fragments.size

        override fun getItem(position: Int) = fragments[position]
    }
}