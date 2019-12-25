package com.sun.binding.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.AppUtils
import com.sun.binding.R
import com.sun.binding.constants.MAIN_BACK_PRESS_INTERVAL_MS
import com.sun.binding.databinding.MainActivityBinding
import com.sun.binding.model.main.MainViewModel
import com.sun.binding.tools.ext.setMainColorStatusBar
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.tools.ext.start
import com.sun.binding.tools.ext.toToastMsg
import com.sun.binding.tools.tool.FragVpAdapter
import com.sun.binding.tools.tool.getString
import com.sun.binding.tools.util.event.EventObserver
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.ui.circle.CircleFragment
import com.sun.binding.ui.course.TaskSubmitActivity
import com.sun.binding.ui.educ.EducFragment
import com.sun.binding.ui.home.HomeFragment
import com.sun.binding.ui.mine.MineFragment
import com.tencent.mmkv.MMKV
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {
    override val viewModel: MainViewModel by viewModel()

    /** 上次点击返回键的时间 */
    private var lastBackPressMs = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // 初始化 MMKV
        MMKV.initialize(mContext)
        // 开始定位 获得坐标
        viewModel.startLocation(mContext)

        // 配置 ViewPager 适配器
        mBinding.cvpMain.adapter = FragVpAdapter.newBuilder()
            .manager(supportFragmentManager)
            .frags(
                arrayListOf(
                    HomeFragment.newInstance(),
                    EducFragment.newInstance(),
                    CircleFragment.newInstance(),
                    MineFragment.newInstance()
                )
            )
            .build()
    }

    override fun initObserve() {
        viewModel.apply {
            navTabIndex.observe(this@MainActivity, Observer { tabIndex ->
                if (tabIndex == 3) {
                    setMainColorStatusBar()
                } else {
                    setWhiteStatusBar()
                }
            })

            submitTarget.observe(this@MainActivity, EventObserver {
                start(TaskSubmitActivity::class.java)
            })
        }
    }

    override fun onBackPressed() {
        val currentBackPressMs = System.currentTimeMillis()
        if ((currentBackPressMs - lastBackPressMs).absoluteValue > MAIN_BACK_PRESS_INTERVAL_MS) {
            // 提示
            viewModel.toastData.postValue(R.string.app_press_again_to_exit.getString().toToastMsg())
            // 保存时间
            lastBackPressMs = currentBackPressMs
        } else {
            // 退出App
            super.onBackPressed()
            AppUtils.exitApp()
        }
    }
}
