package com.sun.binding.widget.state

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.sun.binding.R

/**
 * 默认的空数据页面
 */
class StateEmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    private var ivEmpty: ImageView
    private var tvEmpty: TextView

    init {
        orientation = VERTICAL
        val emptyView = LayoutInflater.from(context).inflate(R.layout.stateful_empty_layout, this, true)
        ivEmpty = emptyView.findViewById(R.id.ivEmpty)
        tvEmpty = emptyView.findViewById(R.id.tvEmpty)
    }

    fun updateEmptyOption(@DrawableRes emptyDrawableRes: Int, emptyText: CharSequence) {
        ivEmpty.setImageResource(emptyDrawableRes)
        tvEmpty.text = emptyText
    }
}