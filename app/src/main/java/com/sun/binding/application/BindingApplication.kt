package com.sun.binding.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils

/**
 * Custom Application
 */
class BindingApplication : Application() {

    companion object {
        fun getInstance(): Application = Utils.getApp()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        // Dex 分包
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        ApplicationSolver(this).init()
    }
}