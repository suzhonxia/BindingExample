package com.sun.binding.ui.main

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.sun.binding.R
import com.sun.binding.databinding.SplashActivityBinding
import com.sun.binding.model.main.SplashViewModel
import com.sun.binding.tools.ext.start
import com.sun.binding.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, SplashActivityBinding>() {
    override val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
        setContentView(R.layout.splash_activity)

        viewModel.delay {
            start(MainActivity::class.java, isFinished = true)
        }
    }
}