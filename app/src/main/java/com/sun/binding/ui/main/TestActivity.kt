package com.sun.binding.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sun.binding.R
import com.sun.binding.tools.ext.showToast
import com.sun.binding.tools.tool.setLocationMode
import com.sun.binding.widget.state.StateEnum
import kotlinx.android.synthetic.main.test_activity.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)

        statefulLayout.setLocationMode {
            "去开启".showToast()
        }

        btnContent.setOnClickListener { statefulLayout.viewState = StateEnum.CONTENT }
        btnLoading.setOnClickListener { statefulLayout.viewState = StateEnum.LOADING }
        btnEmpty.setOnClickListener { statefulLayout.viewState = StateEnum.EMPTY }
        btnError.setOnClickListener { statefulLayout.viewState = StateEnum.ERROR }
    }
}