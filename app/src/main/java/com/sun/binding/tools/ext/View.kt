@file:JvmName("ViewExt")

package com.sun.binding.tools.ext

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout

/**
 * 当前 View 是否被覆盖
 */
fun View.isViewCovered(): Boolean {

    /**
     * 获取 View 在父布局中的位置
     */
    fun indexOfViewInParent(view: View, parent: ViewGroup): Int {
        var index = 0
        while (index < parent.childCount) {
            if (parent.getChildAt(index) === view)
                break
            index++
        }
        return index
    }

    var currentView = this

    val currentViewRect = Rect()
    val partVisible = currentView.getGlobalVisibleRect(currentViewRect)
    val totalHeightVisible = currentViewRect.bottom - currentViewRect.top >= this.measuredHeight
    val totalWidthVisible = currentViewRect.right - currentViewRect.left >= this.measuredWidth
    val totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible
    if (!totalViewVisible) {
        // 如果视图的任何部分被其父视图的任何部分剪切，返回true
        return true
    }

    while (currentView.parent is ViewGroup) {
        val currentParent = currentView.parent as ViewGroup
        if (currentParent.visibility != View.VISIBLE) {
            // 如果视图的父视图不可见，则返回true
            return true
        }

        val start = indexOfViewInParent(currentView, currentParent)
        for (i in start + 1 until currentParent.childCount) {
            val viewRect = Rect()
            this.getGlobalVisibleRect(viewRect)
            val otherView = currentParent.getChildAt(i)
            val otherViewRect = Rect()
            otherView.getGlobalVisibleRect(otherViewRect)
            if (Rect.intersects(viewRect, otherViewRect)) {
                // 如果视图与其哥哥相交(已覆盖)，返回true
                return true
            }
        }
        currentView = currentParent
    }
    return false
}

/**
 * 隐藏软键盘
 */
fun View?.hideSoftKeyboard() {
    if (this == null) {
        return
    }
    this.clearFocus()
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(this.windowToken, 0)
}

/**
 * 构造一个居中的相对布局 Params
 */
fun createCenterRelativeLayoutParams(width: Int = ViewGroup.LayoutParams.WRAP_CONTENT, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT): RelativeLayout.LayoutParams {
    val params = RelativeLayout.LayoutParams(width, height)
    params.addRule(RelativeLayout.CENTER_IN_PARENT)
    return params
}