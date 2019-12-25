package com.sun.binding.ui.course

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.databinding.SuitCourseActivityBinding
import com.sun.binding.entity.SuitCourseEntity
import com.sun.binding.model.course.SuitCourseViewModel
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.widget.decoration.SuitIndexItemDecoration
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

        viewModel.refreshing.set(true)
    }

    override fun initObserve() {
        viewModel.suitCourseList.observe(this, Observer {
            suitAdapter.setNewData(it)
        })
    }
}

internal class SuitCourseAdapter(data: List<SuitCourseEntity>?) : BaseQuickAdapter<SuitCourseEntity, BaseViewHolder>(R.layout.course_suit_index_item, data) {
    override fun convert(helper: BaseViewHolder, item: SuitCourseEntity?) {
        helper.setText(R.id.tvSuitName, item?.name)
    }
}