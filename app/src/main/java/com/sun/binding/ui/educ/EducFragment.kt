package com.sun.binding.ui.educ

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.constants.KeyConstant.KEY_ID
import com.sun.binding.databinding.EducFragmentBinding
import com.sun.binding.entity.EducEntity
import com.sun.binding.model.educ.EducViewModel
import com.sun.binding.tools.ext.start
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.tool.getDrawable
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

    override val viewModel: EducViewModel by viewModel()

    override val layoutResId: Int = R.layout.educ_fragment

    /** 亲职教育列表适配器 */
    private val educAdapter: EducAdapter = EducAdapter(mutableListOf())

    /** 亲职教育列表 item 装饰 */
    private val educItemDecoration: EducIndexItemDecoration = EducIndexItemDecoration()

    override fun initView() {
        mBinding.run {
            adapter = educAdapter.apply {
                setOnItemClickListener { _, _, position ->
                    start(EducCourseActivity::class.java, mapOf(KEY_ID to educAdapter.data[position].id))
                }
            }
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

/**
 * 亲职教育 Adapter
 */
internal class EducAdapter(data: MutableList<EducEntity>?) : BaseQuickAdapter<EducEntity, BaseViewHolder>(R.layout.educ_index_item, data) {
    override fun convert(helper: BaseViewHolder, item: EducEntity?) {
        GlideHelper.loadImage(helper.getView(R.id.ivBackdrop), item?.image, R.drawable.educ_index_item_backdrop.getDrawable())
        GlideHelper.loadImage(helper.getView(R.id.ivEducCover), item?.icon, R.drawable.app_placeholder_course.getDrawable())
        helper.setText(R.id.tvEducTitle, item?.name ?: "")
        helper.setText(R.id.tvEducDesc, item?.content ?: "")
    }
}