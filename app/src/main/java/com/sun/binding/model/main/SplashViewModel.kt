package com.sun.binding.model.main

import androidx.lifecycle.viewModelScope
import com.sun.binding.constants.SPLASH_DELAY_MS
import com.sun.binding.mvvm.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 欢迎页面 ViewModel
 */
class SplashViewModel : BaseViewModel() {

    fun delay(call: () -> Unit) {
        viewModelScope.launch {
            delay(SPLASH_DELAY_MS)
            call.invoke()
        }
    }
}