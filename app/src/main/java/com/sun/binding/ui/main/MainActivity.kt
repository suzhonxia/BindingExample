package com.sun.binding.ui.main

import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.sun.binding.R
import com.sun.binding.constants.MAIN_BACK_PRESS_INTERVAL_MS
import com.sun.binding.databinding.MainActivityBinding
import com.sun.binding.model.main.MainViewModel
import com.sun.binding.tools.ext.toSnackbarMsg
import com.sun.binding.tools.ext.toToastMsg
import com.sun.binding.tools.tool.FragVpAdapter
import com.sun.binding.tools.tool.getString
import com.sun.binding.ui.base.BaseActivity
import com.tencent.mmkv.MMKV
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue
import kotlin.system.exitProcess

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>() {
    override val viewModel: MainViewModel by viewModel()

    /** 上次点击返回键的时间 */
    private var lastBackPressMs = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // 初始化 MMKV
        MMKV.initialize(mContext)

        // 配置 ViewPager 适配器
        mBinding.cvpMain.adapter = FragVpAdapter.newBuilder()
            .manager(supportFragmentManager)
            .frags(
                arrayListOf(

                )
            )
            .build()
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