package com.sun.binding.ui.course

import android.os.Bundle
import androidx.lifecycle.Observer
import com.sun.binding.R
import com.sun.binding.databinding.SuitCourseActivityBinding
import com.sun.binding.model.course.SuitCourseViewModel
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.tools.tool.setCommonMode
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.widget.decoration.SuitIndexItemDecoration
import com.sun.binding.widget.state.StateEnum
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 磁力片套装课程
 */
class SuitCourseActivity : BaseActivity<SuitCourseViewModel, SuitCourseActivityBinding>() {
    override val viewModel: SuitCourseViewModel by viewModel()

    /** 磁力片套装列表适配器 */
    private val suitAdapter: SuitCourseAdapter = SuitCourseAdapter(listOf())

    /** 磁力片套装列表 item 装饰 */
    private val suitItemDecoration: SuitIndexItemDecoration = SuitIndexItemDecoration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.suit_course_activity)
        setWhiteStatusBar()

        initView()
    }

    private fun initView() {
        mBinding.run {
            adapter = suitAdapter
            itemDecoration = suitItemDecoration
        }

        mBinding.statefulLayout.setCommonMode {
            viewModel.viewState.set(StateEnum.LOADING)
            viewModel.refreshing.set(true)
        }
        viewModel.refreshing.set(true)
    }

    override fun initObserve() {
        viewModel.suitCourseList.observe(this, Observer {
            suitAdapter.setNewData(it)
        })
    }
}