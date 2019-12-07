package com.sun.binding.ui.educ

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.sun.binding.R
import com.sun.binding.databinding.EducCourseActivityBinding
import com.sun.binding.model.educ.EducCourseViewModel
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 亲职教育 课程列表
 */
class EducCourseActivity : BaseActivity<EducCourseViewModel, EducCourseActivityBinding>() {
    override val viewModel: EducCourseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educ_course_activity)
        setWhiteStatusBar()

        initView()
    }

    private fun initView() {
        viewModel.run {
            setIntentData(intent.extras)
            sortSelector.clickAction.set {
            }
            categorySelector.clickAction.set {
            }
            ageSelector.clickAction.set {
            }
        }
    }
}