package com.sun.binding.ui.educ

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lxj.xpopup.XPopup
import com.sun.binding.R
import com.sun.binding.databinding.EducCourseActivityBinding
import com.sun.binding.entity.EducOptionEntity
import com.sun.binding.entity.OptionEntity
import com.sun.binding.entity.SelectorEntity
import com.sun.binding.model.educ.EducCourseViewModel
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.widget.dialog.AttachOptionPopupWindow
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educ_course_activity)
        setWhiteStatusBar()

        initView()
    }

    private fun initView() {
        viewModel.run {
            setIntentData(intent.extras)
            educOption.observe(mContext, Observer {
                applySelectWindow(it)
            })

            viewModel.refreshConfig.refreshing.set(true)
        }
    }

    private fun applySelectWindow(educOption: EducOptionEntity) {
        viewModel.run {
            if (!this@EducCourseActivity::sortWindow.isInitialized) {
                sortWindow = generateWindow(educOption.sort, sortSelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            sortSelector.updateStyle(true, it.name)
                        } else {
                            categorySelector.updateStyle(false, optionList?.get(0)?.name ?: "排序")
                        }
                    })
                }
            }
            if (!this@EducCourseActivity::categoryWindow.isInitialized) {
                categoryWindow = generateWindow(educOption.mukuai, categorySelector).apply {
                    viewModel.queryNormalCategory()?.let { selectedOption.value = it }
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            categorySelector.updateStyle(true, it.name)
                            if (this@EducCourseActivity::ageWindow.isInitialized) {
                                ageWindow.updateSelectedOption(null)
                            }
                        } else {
                            categorySelector.updateStyle(false, optionList?.get(0)?.name ?: "分类")
                        }
                    })
                }
            }
            if (!this@EducCourseActivity::ageWindow.isInitialized) {
                ageWindow = generateWindow(educOption.age, ageSelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            ageSelector.updateStyle(true, it.name)
                        } else {
                            ageSelector.updateStyle(false, optionList?.get(0)?.name ?: "年龄段")
                        }
                    })
                }
            }

            sortSelector.clickAction.set { sortWindow.show() }
            categorySelector.clickAction.set { categoryWindow.show() }
            ageSelector.clickAction.set { ageWindow.show() }

            sortSelector.updateStyle(false, educOption.sort[0].name)
            categorySelector.updateStyle(false, educOption.mukuai[0].name)
            ageSelector.updateStyle(false, educOption.age[0].name)
            viewModel.queryNormalCategory()?.let { categorySelector.updateStyle(true, it.name) }
        }
    }

    // 创建选项弹出层
    private fun generateWindow(optionList: MutableList<OptionEntity>, selector: SelectorEntity) =
        XPopup.Builder(mContext)
            .atView(mBinding.optionLayout)
            .asCustom(AttachOptionPopupWindow(mContext, optionList, {
                selector.expanded = true
            }, {
                selector.expanded = false
            })) as AttachOptionPopupWindow
}