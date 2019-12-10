package com.sun.binding.ui.educ

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SizeUtils
import com.lxj.xpopup.XPopup
import com.sun.binding.R
import com.sun.binding.databinding.EducCourseActivityBinding
import com.sun.binding.entity.CourseConfigEntity
import com.sun.binding.entity.EducOptionEntity
import com.sun.binding.entity.OptionEntity
import com.sun.binding.entity.SelectorEntity
import com.sun.binding.model.educ.EducCourseViewModel
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.tools.ext.showToast
import com.sun.binding.tools.ext.toToastMsg
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.ui.course.CourseAdapter
import com.sun.binding.widget.decoration.GridSpaceItemDecoration
import com.sun.binding.widget.dialog.AttachOptionPopupWindow
import com.sun.binding.widget.state.StateEnum
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 亲职教育 课程列表
 */
class EducCourseActivity : BaseActivity<EducCourseViewModel, EducCourseActivityBinding>() {
    override val viewModel: EducCourseViewModel by viewModel()

    /** 排序弹出层 */
    private lateinit var sortWindow: AttachOptionPopupWindow

    /** 分类弹出层 */
    private lateinit var categoryWindow: AttachOptionPopupWindow

    /** 年龄弹出层 */
    private lateinit var ageWindow: AttachOptionPopupWindow

    /** 课程列表适配器 */
    private val educCourseAdapter = CourseAdapter(mutableListOf(), CourseConfigEntity(hasPart = false, singleLine = false))

    /** 列表 item 装饰 */
    private val courseItemDecoration = GridSpaceItemDecoration(SizeUtils.dp2px(10F), true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educ_course_activity)
        setWhiteStatusBar()

        initView()
    }

    private fun initView() {
        mBinding.run {
            adapter = educCourseAdapter.apply {
                setOnItemClickListener { _, _, position ->
                    "跳转到课程详情 id = ${educCourseAdapter.data[position]}".showToast()
                }
            }
            itemDecoration = courseItemDecoration
        }

        viewModel.getEducCourseOption()
    }

    override fun initObserve() {
        viewModel.run {
            educOption.observe(mContext, Observer {
                applySelectWindow(it)
            })
            educCourseList.observe(mContext, Observer {
                if (isRefreshFlag()) {
                    educCourseAdapter.setNewData(it)
                } else {
                    educCourseAdapter.addData(it)
                }
            })
            retryTarget.observe(mContext, Observer {
                viewModel.refreshConfig.refreshing.set(true)
            })
        }
    }

    private fun applySelectWindow(educOption: EducOptionEntity) {
        viewModel.run {
            if (!this@EducCourseActivity::sortWindow.isInitialized) {
                sortWindow = generateWindow(educOption.sort, sortSelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            // 观察到数据变化，进行列表数据请求
                            refresh()

                            sortSelector.updateStyleAndData(true, it.name, it.id)
                        } else {
                            categorySelector.updateStyleAndData(false, optionList?.get(0)?.name ?: "排序", 0)
                        }
                    })
                }
            }
            if (!this@EducCourseActivity::categoryWindow.isInitialized) {
                categoryWindow = generateWindow(educOption.mukuai, categorySelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            // 2.1.2 观察到数据变化，则进行列表数据请求(一般初始化进来就会调用一次)
                            refresh()

                            categorySelector.updateStyleAndData(true, it.name, it.id)
                            // 每次选择类别，都需要重置年龄段数据
                            if (this@EducCourseActivity::ageWindow.isInitialized) {
                                ageWindow.updateSelectedOption(null)
                            }
                        } else {
                            categorySelector.updateStyleAndData(false, optionList?.get(0)?.name ?: "分类", 0)
                        }
                    })
                }
            }
            if (!this@EducCourseActivity::ageWindow.isInitialized) {
                ageWindow = generateWindow(educOption.age, ageSelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            // 观察到数据变化，进行列表数据请求
                            refresh()

                            ageSelector.updateStyleAndData(true, it.name, it.id)
                        } else {
                            ageSelector.updateStyleAndData(false, optionList?.get(0)?.name ?: "年龄段", 0)
                        }
                    })
                }
            }

            sortSelector.clickAction.set { sortWindow.show() }
            categorySelector.clickAction.set { categoryWindow.show() }
            ageSelector.clickAction.set { ageWindow.show() }

            sortSelector.updateStyleAndData(false, educOption.sort[0].name, 0)
            categorySelector.updateStyleAndData(false, educOption.mukuai[0].name, 0)
            ageSelector.updateStyleAndData(false, educOption.age[0].name, 0)

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

    private fun refresh() {
        viewModel.run {
            if (viewState.get() != StateEnum.CONTENT) {
                viewModel.viewState.set(StateEnum.LOADING)
            }
            viewModel.refreshConfig.refreshing.set(true)
        }
    }
}