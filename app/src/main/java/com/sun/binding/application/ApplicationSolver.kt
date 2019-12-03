package com.sun.binding.application

import android.annotation.SuppressLint
import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.hjq.bar.TitleBar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.sun.binding.BuildConfig
import com.sun.binding.R
import com.sun.binding.tools.manager.AppStackManager
import com.sun.binding.widget.LoadMoreEmptyView
import com.sun.binding.widget.TitleBarStyleImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Application 初始化逻辑
 */
class ApplicationSolver(private val app: Application) : Runnable {
    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                ClassicsHeader(context).setPrimaryColorId(R.color.app_window_bg)
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                layout.setEnableLoadMoreWhenContentNotFull(true)
                layout.setEnableFooterFollowWhenNoMoreData(true)
                LoadMoreEmptyView(context)
            }
        }
    }

    override fun run() {
        initAppInfo()
    }

    fun init() {
        if (app.packageName != AppStackManager.getCurProcessName(app)) {
            return
        }

        Thread(this).start()
        closeAndroidPDialog()
    }

    private fun initAppInfo() {
        // 注册 Activity 管理类
        AppStackManager.register(app)

        // 初始化 Koin
        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(app)
            modules(listOf(netModule, repositoryModule, adapterModule, viewModelModule))
        }

        // 初始化输出日志
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG

        // TitleBar 设置Style
        TitleBar.initStyle(TitleBarStyleImpl(app))
    }

    /**
     * 解决Android p debug环境弹框提示的问题
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun closeAndroidPDialog() {
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}