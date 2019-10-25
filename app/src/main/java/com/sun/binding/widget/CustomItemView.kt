package com.sun.binding.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.sun.binding.R
import com.sun.binding.databinding.AppLayoutCustomItemViewBinding

/**
 * 自定义 ItemView
 */
class CustomItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private lateinit var mBinding: AppLayoutCustomItemViewBinding

    private var iconRes: Int = 0
    private var tipColor: Int = 0
    private var tipText: String? = null
    private var labelText: String? = null
    private var showTip: Boolean = false
    private var showIcon: Boolean = false
    private var showLine: Boolean = false
    private var lineMargin: Boolean = false

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView)
        iconRes = attributes.getResourceId(R.styleable.CustomItemView_iconRes, R.drawable.mine_icon_bounty)
        tipColor = attributes.getColor(R.styleable.CustomItemView_tipColor, -0x10000)
        tipText = attributes.getString(R.styleable.CustomItemView_tipText)
        labelText = attributes.getString(R.styleable.CustomItemView_labelText)
        showTip = attributes.getBoolean(R.styleable.CustomItemView_showTip, false)
        showIcon = attributes.getBoolean(R.styleable.CustomItemView_showIcon, true)
        showLine = attributes.getBoolean(R.styleable.CustomItemView_showLine, true)
        lineMargin = attributes.getBoolean(R.styleable.CustomItemView_lineMargin, false)
        attributes.recycle()

        applyView()
    }

    private fun applyView() {
        mBinding = AppLayoutCustomItemViewBinding.inflate(LayoutInflater.from(context), this, false)
        addView(mBinding.root)

        mBinding.ivIcon.setImageResource(iconRes)
        mBinding.ivIcon.visibility = if (showIcon) View.VISIBLE else View.GONE
        mBinding.tvLabel.text = labelText
        if (showTip) {
            mBinding.tvTip.visibility = View.VISIBLE
            mBinding.tvTip.text = tipText
            mBinding.tvTip.setTextColor(tipColor)
        } else {
            mBinding.tvTip.visibility = View.GONE
        }
        if (showLine) {
            mBinding.line.visibility = View.VISIBLE
        } else {
            mBinding.line.visibility = View.GONE
        }
        if (lineMargin) {
            (mBinding.line.layoutParams as MarginLayoutParams).setMargins(SizeUtils.dp2px(15f), 0, SizeUtils.dp2px(15f), 0)
        }
    }

    fun setTipText(tipText: String) {
        this.tipText = tipText
        mBinding.tvTip.text = tipText
    }

    fun setTipTextAndShow(tipText: String) {
        this.tipText = tipText
        mBinding.tvTip.text = tipText
        mBinding.tvTip.visibility = View.VISIBLE
    }

    fun getIvIcon(): ImageView {
        return mBinding.ivIcon
    }

    fun getTvLabel(): TextView {
        return mBinding.tvLabel
    }

    fun getTvTip(): TextView {
        return mBinding.tvTip
    }

    fun showLineMargin() {
        lineMargin = true
        (mBinding.line.layoutParams as MarginLayoutParams).setMargins(SizeUtils.dp2px(15f), 0, SizeUtils.dp2px(15f), 0)
    }
}