package com.sun.binding.ui.home

import com.sun.binding.R
import com.sun.binding.databinding.HomeFragmentBinding
import com.sun.binding.model.home.HomeViewModel
import com.sun.binding.mvvm.model.ProgressModel
import com.sun.binding.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 主界面 - 首页 Tab
 */
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         */
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override val viewModel: HomeViewModel by viewModel()

    override val layoutResId: Int = R.layout.home_fragment

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun initView() {
        viewModel.progressData.postValue(ProgressModel(true))
    }

    override fun initObserve() {

    }
}