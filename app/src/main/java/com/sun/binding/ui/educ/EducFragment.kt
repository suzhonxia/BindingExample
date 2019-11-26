package com.sun.binding.ui.educ

import androidx.lifecycle.Observer
import com.sun.binding.R
import com.sun.binding.databinding.EducFragmentBinding
import com.sun.binding.model.educ.EducViewModel
import com.sun.binding.ui.base.BaseFragment
import com.sun.binding.widget.decoration.EducIndexItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 主界面 - 亲职教育 Tab
 */
class EducFragment : BaseFragment<EducViewModel, EducFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(): EducFragment {
            return EducFragment()
        }
    }

    /** 亲职教育列表适配器 */
    private val educAdapter: EducAdapter = EducAdapter(listOf())

    /** 亲职教育列表 item 装饰 */
    private val educItemDecoration: EducIndexItemDecoration = EducIndexItemDecoration()

    override val viewModel: EducViewModel by viewModel()

    override val layoutResId: Int = R.layout.educ_fragment

    override fun initView() {
        mBinding.run {
            adapter = educAdapter
            itemDecoration = educItemDecoration
        }

        viewModel.refreshing.set(true)
    }

    override fun initObserve() {
        viewModel.educCategoryList.observe(this, Observer {
            educAdapter.setNewData(it)
        })
    }
}