package com.sun.binding.widget.state

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.sun.binding.R

/**
 * 默认的错误页面
 */
class StateErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    private var ivError: ImageView
    private var tvError: TextView
    private var tvAction: TextView

    init {
        orientation = VERTICAL
        val errorView = LayoutInflater.from(context).inflate(R.layout.stateful_error_layout, this, true)
        ivError = errorView.findViewById(R.id.ivError)
        tvError = errorView.findViewById(R.id.tvError)
        tvAction = errorView.findViewById(R.id.tvAction)
    }

    fun updateErrorOption(@DrawableRes errorDrawableRes: Int, errorText: CharSequence, errorActionText: String = "", errorAction: (() -> Unit)? = null) {
        ivError.setImageResource(errorDrawableRes)
        tvError.text = errorText
        if (errorActionText.isEmpty() || errorAction == null) {
            tvAction.visibility = View.GONE
        } else {
            tvAction.visibility = View.VISIBLE
            tvAction.text = errorActionText
            tvAction.setOnClickListener { errorAction.invoke() }
        }
    }
}