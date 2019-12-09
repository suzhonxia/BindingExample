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
                            setHighlight(sortSelector, it.name)
                        } else {
                            setNormal(sortSelector, optionList?.get(0)?.name ?: "排序")
                        }
                    })
                }
            }
            if (!this@EducCourseActivity::categoryWindow.isInitialized) {
                categoryWindow = generateWindow(educOption.mukuai, categorySelector).apply {
                    viewModel.queryNormalCategory()?.let { selectedOption.value = it }
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            setHighlight(categorySelector, it.name)
                            if (this@EducCourseActivity::ageWindow.isInitialized) {
                                ageWindow.updateSelectedOption(null)
                            }
                        } else {
                            setNormal(categorySelector, optionList?.get(0)?.name ?: "分类")
                        }
                    })
                }
            }
            if (!this@EducCourseActivity::ageWindow.isInitialized) {
                ageWindow = generateWindow(educOption.age, ageSelector).apply {
                    selectedOption.observe(mContext, Observer {
                        if (it != null) {
                            setHighlight(ageSelector, it.name)
                        } else {
                            setNormal(ageSelector, optionList?.get(0)?.name ?: "年龄段")
                        }
                    })
                }
            }

            sortSelector.clickAction.set { sortWindow.show() }
            categorySelector.clickAction.set { categoryWindow.show() }
            ageSelector.clickAction.set { ageWindow.show() }

            setNormal(sortSelector, educOption.sort[0].name)
            setNormal(categorySelector, educOption.mukuai[0].name)
            setNormal(ageSelector, educOption.age[0].name)
            viewModel.queryNormalCategory()?.let { setHighlight(categorySelector, it.name) }
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

    private fun setHighlight(selector: SelectorEntity, text: String) {
        selector.run {
            highlight = true
            name.set(text)
        }
    }

    private fun setNormal(selector: SelectorEntity, text: String) {
        selector.run {
            highlight = false
            name.set(text)
        }
    }
}