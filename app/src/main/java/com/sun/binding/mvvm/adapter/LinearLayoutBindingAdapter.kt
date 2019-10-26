@file:Suppress("unused")

package com.sun.binding.mvvm.adapter

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.SizeUtils
import com.sun.binding.databinding.MineItemShopNavBinding
import com.sun.binding.entity.UserMenuEntity
import com.sun.binding.entity.UserShopNavEntity
import com.sun.binding.tools.ext.showToast
import com.sun.binding.widget.CustomItemView

/**
 * LinearLayout DataBinding 适配器
 */

/**
 * 添加 child : 我的 - 店铺 - Tab
 *
 * @param parent [LinearLayout] 对象
 */
@BindingAdapter("android:bind_mine_shop_nav")
fun addMineShopNavList(parent: LinearLayout, navList: List<UserShopNavEntity>?) {
    parent.removeAllViews()
    navList?.forEach { shopNav ->
        val lp = LinearLayout.LayoutParams(0, SizeUtils.dp2px(54F))
        lp.weight = 1F

        val shopNavBinding = MineItemShopNavBinding.inflate(LayoutInflater.from(parent.context), null, false)
        shopNavBinding.nav = shopNav
        shopNavBinding.root.setOnClickListener {
            if (!shopNav.url.isNullOrEmpty()) shopNav.url.showToast()
        }
        parent.addView(shopNavBinding.root, lp)
    }
}

/**
 * 添加 child : 我的 - 店铺 - Tab
 *
 * @param parent [LinearLayout] 对象
 */
@BindingAdapter("android:bind_mine_menu")
fun addMineMenuList(parent: LinearLayout, menuList: List<UserMenuEntity>?) {
    parent.removeAllViews()
    menuList?.forEach { menu ->
        if (menu.shouldShow()) {
            val menuItem = CustomItemView(parent.context)
            menuItem.loadData(menu)
            menuItem.setOnClickListener {
                if (!menu.url.isNullOrEmpty()) menu.url.showToast()
            }
            parent.addView(menuItem)
        }
    }
}