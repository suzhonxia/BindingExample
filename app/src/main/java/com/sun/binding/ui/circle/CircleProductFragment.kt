package com.sun.binding.ui.circle

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.sun.binding.R
import com.sun.binding.databinding.CircleProductFragmentBinding
import com.sun.binding.model.circle.CircleProductViewModel
import com.sun.binding.ui.base.BaseFragment
import com.sun.binding.widget.decoration.CircleProductItemDecoration
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

    /** 课程列表适配器 */
    private val circleProductAdapter: CircleProductAdapter = CircleProductAdapter(listOf())

    /** 列表 item 装饰 */
    private val circleProductItemDecoration = CircleProductItemDecoration()

    override fun initView() {
        mBinding.run {
            adapter = circleProductAdapter
            itemDecoration = circleProductItemDecoration
        }

        viewModel.type = arguments?.get("type") as? Int ?: 0
        viewModel.refreshing.set(true)
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
}