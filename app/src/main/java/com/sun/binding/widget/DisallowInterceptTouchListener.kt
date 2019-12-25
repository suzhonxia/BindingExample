package com.sun.binding.widget

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

/**
 * 兼容滑动冲突
 */
class DisallowInterceptTouchListener : View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                v?.parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                v?.parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_UP -> {
                v?.parent?.requestDisallowInterceptTouchEvent(false)
            }
        }
        return false
    }
}