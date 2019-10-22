package com.sun.binding.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sun.binding.BuildConfig
import com.sun.binding.tools.manager.AppManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Custom Application
 */
class BindingApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        // Dex 分包
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        // 注册 Activity 管理类
        AppManager.register(this)

        // 初始化 Koin
        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@BindingApplication)
            modules(listOf(netModule, repositoryModule, adapterModule, viewModelModule))
        }

        // 初始化LiveDataBus
        LiveEventBus.config().lifecycleObserverAlwaysActive(true).autoClear(false)

        // 初始化输出日志
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG
    }
}