package com.sun.binding.ui.course

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SizeUtils
import com.lxj.xpopup.XPopup
import com.sun.binding.R
import com.sun.binding.databinding.CategoryCourseActivityBinding
import com.sun.binding.entity.*
import com.sun.binding.model.course.CategoryCourseViewModel
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.tools.ext.showToast
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.widget.decoration.GridSpaceItemDecoration
import com.sun.binding.widget.dialog.AttachFilterPopupWindow
import com.sun.binding.widget.dialog.AttachOptionPopupWindow
import com.sun.binding.widget.state.StateEnum
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 课程分类 课程列表
 */
class CategoryCourseActivity : BaseActivity<CategoryCourseViewModel, CategoryCourseActivityBinding>() {
    override val viewModel: CategoryCourseViewModel by viewModel()

    /** 排序弹出层 */
    private lateinit var orderWindow: AttachOptionPopupWindow

    /** 分类弹出层 */
    private lateinit var categoryWindow: AttachOptionPopupWindow

    /** 筛选弹出层 */
    private lateinit var filterWindow: AttachFilterPopupWindow

    /** 课程列表适配器 */
    private val categoryCourseAdapter = CourseAdapter(mutableListOf(), CourseConfigEntity())

    /** 列表 item 装饰 */
    private val courseItemDecoration = GridSpaceItemDecoration(SizeUtils.dp2px(10F), true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_course_activity)
        setWhiteStatusBar()

        initView()
    }

    private fun initView() {
        mBinding.run {
            adapter = categoryCourseAdapter.apply {
                setOnItemClickListener { _, _, position ->
                    "跳转到课程详情 id = ${categoryCourseAdapter.data[position]}".showToast()
                }
            }
            itemDecoration = courseItemDecoration
        }

        viewModel.getEducCourseOption()
    }

    override fun initObserve() {
        viewModel.run {
            categoryOption.observe(mContext, Observer {
                applySelectWindow(it)
            })
            categoryCourseList.observe(mContext, Observer {
                if (isRefreshFlag()) {
                    categoryCourseAdapter.setNewData(it)
                } else {
                    categoryCourseAdapter.addData(it)
                }
            })
        }
    }

    private fun applySelectWindow(categoryOption: CategoryOptionEntity) {
        val orderList = categoryOption.getOrderList()
        val categoryList = categoryOption.getCategoryList()
        val filterList = categoryOption.getFilterList(viewModel.queryNormalCategory())

        viewModel.run {
            if (!this@CategoryCourseActivity::orderWindow.isInitialized) {
                orderWindow = generateWindow(orderList, orderSelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            // 观察到数据变化，进行列表数据请求
                            refresh()

                            orderSelector.updateStyleAndData(true, it.name, it.key)
                        } else {
                            orderSelector.updateStyleAndData(true, optionList?.get(0)?.name ?: "排序", "")
                        }
                    })
                }
            }
            if (!this@CategoryCourseActivity::categoryWindow.isInitialized) {
                categoryWindow = generateWindow(categoryList, categorySelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            // 观察到数据变化，进行列表数据请求
                            refresh()

                            categorySelector.updateStyleAndData(true, it.name, it.id)
                        } else {
                            categorySelector.updateStyleAndData(true, optionList?.get(0)?.name ?: "分类", 0)
                        }
                    })
                }
            }
            if (!this@CategoryCourseActivity::filterWindow.isInitialized) {
                filterWindow = generateFilterWindow(filterList, filterSelector).apply {
                    //                    selectedOption.observe(mContext, Observer {
//                        if (it != null) {
//                            // 观察到数据变化，进行列表数据请求
//                            refresh()
//
//                            categorySelector.updateStyleAndData(true, it.name, it.id)
//                        } else {
//                            categorySelector.updateStyleAndData(true, optionList?.get(0)?.name ?: "分类", 0)
//                        }
//                    })
                }
            }

            orderSelector.clickAction.set { orderWindow.show() }
            categorySelector.clickAction.set { categoryWindow.show() }
            filterSelector.clickAction.set { filterWindow.show() }

            orderSelector.updateStyleAndData(false, orderList[0].name, "")
            categorySelector.updateStyleAndData(false, categoryList[0].name, 0)
            filterSelector.updateStyleAndData(false, "筛选", "")

            // 2. 是否有选中的分类id
            viewModel.queryNormalCategory()?.let {
                // 2.1.1 如果有，则更新UI和数据
                categorySelector.updateStyleAndData(true, it.name, it.id)
                categoryWindow.selectedOption.value = it
            } ?: viewModel.refreshConfig.refreshing.set(true)// 2.2 如果没有，直接请求第一个分类的数据
        }
    }

    // 创建选项弹出层
    private fun generateWindow(optionList: MutableList<OptionEntity>, selector: SelectorEntity) =
        XPopup.Builder(mContext)
            .atView(mBinding.optionLayout)
            .asCustom(
                AttachOptionPopupWindow(mContext, optionList,
                    { selector.expanded = true },
                    { selector.expanded = false })
            ) as AttachOptionPopupWindow

    // 创建筛选弹出层
    private fun generateFilterWindow(filterList: MutableList<FilterEntity>, selector: SelectorEntity) =
        XPopup.Builder(mContext)
            .atView(mBinding.optionLayout)
            .asCustom(
                AttachFilterPopupWindow(mContext,
                    { selector.expanded = true },
                    { selector.expanded = false }
                ).apply { this.filterList = filterList }
            ) as AttachFilterPopupWindow

    private fun refresh() {
        viewModel.run {
            if (viewState.get() != StateEnum.CONTENT) {
                viewModel.viewState.set(StateEnum.LOADING)
            }
            viewModel.refreshConfig.refreshing.set(true)
        }
    }
}