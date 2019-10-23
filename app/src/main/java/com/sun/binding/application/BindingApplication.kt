package com.sun.binding.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

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

        ApplicationSolver(this).init()
    }
}