package com.sun.binding.tools.util

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.sun.binding.BuildConfig

const val commonTag = "BindingExample"
val isShow: Boolean = BuildConfig.DEBUG

fun showLog(tag: String = commonTag, msg: String) {
    if (isShow) {
        Log.d(tag, msg)
    }
}

fun showDebug(msg: String) {
    if (isShow) {
        LogUtils.d(msg)
    }
}

fun showInfo(msg: String) {
    if (isShow) {
        LogUtils.i(msg)
    }
}

fun showWarning(msg: String) {
    if (isShow) {
        LogUtils.w(msg)
    }
}

fun showError(msg: String) {
    if (isShow) {
        LogUtils.e(msg)
    }
}

fun showDebug(tag: String = commonTag, msg: String) {
    if (isShow) {
        LogUtils.d(tag, msg)
    }
}

fun showInfo(tag: String = commonTag, msg: String) {
    if (isShow) {
        LogUtils.i(tag, msg)
    }
}

fun showWarning(tag: String = commonTag, msg: String) {
    if (isShow) {
        LogUtils.w(tag, msg)
    }
}

fun showError(tag: String = commonTag, msg: String) {
    if (isShow) {
        LogUtils.e(tag, msg)
    }
}

fun showJson(tag: String = commonTag, json: String) {
    if (isShow) {
        LogUtils.json(tag, json)
    }
}