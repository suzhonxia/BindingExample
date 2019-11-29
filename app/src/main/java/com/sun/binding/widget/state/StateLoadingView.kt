package com.sun.binding.widget.state

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.sun.binding.R

/**
 * 默认的 loading 页面
 */
class StateLoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.stateful_loading_layout, this, true)
    }
}