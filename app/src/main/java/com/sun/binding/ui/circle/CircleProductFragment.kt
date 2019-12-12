package com.sun.binding.ui.circle

import android.Manifest
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.blankj.utilcode.constant.PermissionConstants
import com.sun.binding.R
import com.sun.binding.constants.KeyConstant
import com.sun.binding.databinding.CircleProductFragmentBinding
import com.sun.binding.entity.validLocation
import com.sun.binding.model.circle.CircleProductViewModel
import com.sun.binding.tools.helper.PermissionHelper
import com.sun.binding.tools.manager.AppUserManager
import com.sun.binding.tools.util.event.EventObserver
import com.sun.binding.ui.base.BaseFragment
import com.sun.binding.widget.decoration.CircleProductItemDecoration
import com.sun.binding.widget.state.StateEnum
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
            return CircleProductFragment().apply { arguments = bundleOf(KeyConstant.KEY_TYPE to type) }
        }
    }

    override val viewModel: CircleProductViewModel by viewModel()

    override val layoutResId: Int = R.layout.circle_product_fragment

    /** 课程列表适配器 */
    private val circleProductAdapter: CircleProductAdapter = CircleProductAdapter(listOf())

    /** 列表 item 装饰 */
    private val circleProductItemDecoration = CircleProductItemDecoration()

    override fun initView() {
        mBinding.run {
            adapter = circleProductAdapter
            itemDecoration = circleProductItemDecoration
        }

        // 刷新请求
        refresh()
    }

    override fun initObserve() {
        viewModel.run {
            circleProductList.observe(this@CircleProductFragment, Observer {
                if (isRefreshFlag()) {
                    circleProductAdapter.setNewData(it)
                } else {
                    circleProductAdapter.addData(it)
                }
            })
            retryTarget.observe(this@CircleProductFragment, EventObserver {
                refresh()
            })
        }
    }

    private fun refresh() {
        if (!viewModel.needLocation()) {
            viewModel.refreshConfig.refreshing.set(true)
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        val action = {
            if (AppUserManager.getLocation().validLocation()) {
                viewModel.refreshConfig.refreshing.set(true)
            } else {
                viewModel.startLocation(mContext) {
                    viewModel.refreshConfig.refreshing.set(true)
                }
            }
        }
        if (!PermissionHelper.isGranted(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            viewModel.viewState.set(StateEnum.ERROR)
            PermissionHelper.requestPermission(mContext, PermissionConstants.LOCATION) { action() }
        } else {
            viewModel.viewState.set(StateEnum.CONTENT)
            action()
        }
    }
}