package com.sun.binding.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.sun.binding.R
import com.sun.binding.databinding.AppSmartRefreshFooterBinding
import com.sun.binding.tools.ext.createCenterRelativeLayoutParams
import com.sun.binding.tools.tool.getColor

/**
 * 自定义的上拉加载更对界面(支持 noMore 显示)
 */
class LoadMoreEmptyView(context: Context?) : RelativeLayout(context), RefreshFooter {
    private var binding: AppSmartRefreshFooterBinding

    private var noMoreData = false

    init {
        setBackgroundColor(R.color.app_window_bg.getColor())

        binding = AppSmartRefreshFooterBinding.inflate(LayoutInflater.from(context), null, false)
        addView(binding.root, createCenterRelativeLayoutParams(width = ViewGroup.LayoutParams.MATCH_PARENT))
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        if (this.noMoreData != noMoreData) {
            this.noMoreData = noMoreData
            if (noMoreData) {
                binding.tvTip.text = R.string.app_smart_no_more.toString()
                binding.tvTip.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.app_smart_no_more, 0, 0, 0)
                binding.tvGap.visibility = View.VISIBLE
            } else {
                binding.tvTip.text = "上拉加载更多"
                binding.tvTip.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                binding.tvGap.visibility = View.GONE
            }
            binding.footerBar.visibility = View.GONE
        }
        return true
    }

    override fun getView(): View = this

    override fun getSpinnerStyle(): SpinnerStyle = SpinnerStyle.Translate

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        if (!this.noMoreData) {
            binding.footerBar.visibility = View.GONE

            if (!success) {
                binding.tvGap.visibility = View.GONE
                binding.tvTip.text = "加载数据失败"
                binding.tvTip.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
        return 0
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean = false

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        if (!noMoreData) {
            if (newState == RefreshState.PullUpToLoad) {
                binding.tvGap.visibility = View.GONE
                binding.tvTip.text = "上拉加载更多"
                binding.tvTip.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
            } else if (newState == RefreshState.Loading) {
                binding.tvGap.visibility = View.GONE
                binding.tvTip.text = "正在加载中..."
                binding.tvTip.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                binding.footerBar.visibility = View.VISIBLE
            }
        }
    }
}