package com.sun.binding.ui.circle

import android.Manifest
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.blankj.utilcode.constant.PermissionConstants
import com.sun.binding.R
import com.sun.binding.databinding.CircleProductFragmentBinding
import com.sun.binding.entity.validLocation
import com.sun.binding.model.circle.CircleProductViewModel
import com.sun.binding.tools.helper.PermissionHelper
import com.sun.binding.tools.manager.AppUserManager
import com.sun.binding.tools.tool.setCommonMode
import com.sun.binding.tools.tool.setLocationMode
import com.sun.binding.ui.base.BaseFragment
import com.sun.binding.widget.decoration.CircleProductItemDecoration
import com.sun.binding.widget.state.StateEnum
import kotlinx.android.synthetic.main.circle_product_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 同学拳 - Tab
 */
class CircleProductFragment private constructor() : BaseFragment<CircleProductViewModel, CircleProductFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(type: Int): CircleProductFragment {
            return CircleProductFragment().apply { arguments = bundleOf("type" to type) }
        }
    }

    override val viewModel: CircleProductViewModel by viewModel()

    override val layoutResId: Int = R.layout.circle_product_fragment

    /** 当前状态 */
    private var viewState = StateEnum.CONTENT
        set(value) {
            field = value
            statefulLayout.viewState = value
        }

    /** 课程列表适配器 */
    private val circleProductAdapter: CircleProductAdapter = CircleProductAdapter(listOf())

    /** 列表 item 装饰 */
    private val circleProductItemDecoration = CircleProductItemDecoration()

    override fun initView() {
        mBinding.run {
            adapter = circleProductAdapter
            itemDecoration = circleProductItemDecoration
        }

        viewModel.run {
            setIntentData(arguments)

            // 设置 StatefulLayout 模式
            if (!needLocation()) {
                statefulLayout.setCommonMode { refresh() }
            } else {
                statefulLayout.setLocationMode { refresh() }
            }
        }

        // 刷新请求
        refresh()
    }

    override fun initObserve() {
        viewModel.circleProductList.observe(this, Observer {
            if (viewModel.isRefreshFlag()) {
                circleProductAdapter.setNewData(it)
            } else {
                circleProductAdapter.addData(it)
            }
        })
    }

    private fun refresh() {
        if (!viewModel.needLocation()) {
            viewModel.refreshing.set(true)
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        val action = {
            if (AppUserManager.getLocation().validLocation()) {
                viewModel.refreshing.set(true)
            } else {
                viewModel.startLocation(mContext) {
                    viewModel.refreshing.set(true)
                }
            }
        }
        if (!PermissionHelper.isGranted(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            viewState = StateEnum.ERROR
            PermissionHelper.requestPermission(mContext, PermissionConstants.LOCATION) { action() }
        } else {
            viewState = StateEnum.CONTENT
            action()
        }
    }
}