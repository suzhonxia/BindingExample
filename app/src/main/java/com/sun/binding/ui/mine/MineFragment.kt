package com.sun.binding.ui.mine

import com.sun.binding.R
import com.sun.binding.databinding.MineFragmentBinding
import com.sun.binding.model.mine.MineViewModel
import com.sun.binding.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 主界面 - 我的 Tab
 */
class MineFragment : BaseFragment<MineViewModel, MineFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    override val viewModel: MineViewModel by viewModel()

    override val layoutResId: Int = R.layout.mine_fragment

    override fun initView() {

    }
}