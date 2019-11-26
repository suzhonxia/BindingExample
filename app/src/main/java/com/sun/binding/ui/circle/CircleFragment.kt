package com.sun.binding.ui.circle

import com.sun.binding.R
import com.sun.binding.constants.CircleTab
import com.sun.binding.constants.CircleTab.TAB_CIRCLE_ALL
import com.sun.binding.constants.CircleTab.TAB_CIRCLE_FOCUS
import com.sun.binding.constants.CircleTab.TAB_CIRCLE_NEARBY
import com.sun.binding.constants.CircleTab.TAB_CIRCLE_ORIGINAL
import com.sun.binding.databinding.CircleFragmentBinding
import com.sun.binding.model.circle.CircleViewModel
import com.sun.binding.tools.ext.bindViewPager
import com.sun.binding.ui.base.BaseFragment
import kotlinx.android.synthetic.main.circle_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 主界面 - 同学圈 Tab
 */
class CircleFragment : BaseFragment<CircleViewModel, CircleFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(): CircleFragment {
            return CircleFragment()
        }
    }

    override val viewModel: CircleViewModel by viewModel()

    override val layoutResId: Int = R.layout.circle_fragment

    override fun initView() {
        val fragments = listOf(
            CircleProductFragment.newInstance(TAB_CIRCLE_ALL),
            CircleProductFragment.newInstance(TAB_CIRCLE_NEARBY),
            CircleProductFragment.newInstance(TAB_CIRCLE_FOCUS),
            CircleProductFragment.newInstance(TAB_CIRCLE_ORIGINAL)
        )

        indicatorCircle.bindViewPager(mContext, childFragmentManager, cvpCircle, CircleTab.tabNameList, fragments)
    }
}