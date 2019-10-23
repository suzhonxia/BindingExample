package com.sun.binding.model.main

import android.view.MenuItem
import com.sun.binding.R
import com.sun.binding.constants.TAB_MAIN_BOTTOM_CIRCLE
import com.sun.binding.constants.TAB_MAIN_BOTTOM_EDUC
import com.sun.binding.constants.TAB_MAIN_BOTTOM_HOMEPAGE
import com.sun.binding.constants.TAB_MAIN_BOTTOM_MINE
import com.sun.binding.mvvm.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.tools.ext.toToastMsg

/**
 * 主界面 ViewModel
 */
class MainViewModel : BaseViewModel() {

    /** ViewPager 当前位置 */
    val currentItem: BindingField<Int> = BindingField(TAB_MAIN_BOTTOM_HOMEPAGE)

    /** 底部 Tab 选中回调 */
    val itemSelectedListener: (MenuItem) -> Boolean = { menuItem ->
        if (menuItem.itemId == R.id.emptyTab) {
            false
        } else {
            val targetPosition = when (menuItem.itemId) {
                R.id.homeTab -> TAB_MAIN_BOTTOM_HOMEPAGE
                R.id.educTab -> TAB_MAIN_BOTTOM_EDUC
                R.id.circleTab -> TAB_MAIN_BOTTOM_CIRCLE
                R.id.mineTab -> TAB_MAIN_BOTTOM_MINE
                else -> TAB_MAIN_BOTTOM_HOMEPAGE
            }
            if (currentItem.get() != targetPosition) {
                currentItem.set(targetPosition)
            }
            true
        }
    }

    /** 发布按钮点击 */
    val onSubmitTabClick = {
        toastData.postValue("跳转到发布页面".toToastMsg())
    }
}