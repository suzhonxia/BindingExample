package com.sun.binding.tools.helper

import android.app.Activity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.utils.MediaStoreCompat

/**
 * Matisse 封装
 */
object MatisseHelper {

    fun takePhoto(activity: Activity, mediaStoreCompat: MediaStoreCompat, requestCode: Int) {
        mediaStoreCompat.run {
            setCaptureStrategy(CaptureStrategy(true, "com.sun.binding.fileprovider", "Magfx"))
            dispatchCaptureIntent(activity, requestCode)
        }
    }

    fun choosePhoto(activity: Activity, max: Int, requestCode: Int) {
        Matisse.from(activity)
            .choose(MimeType.ofImage())// 设置选择的MIME类型
            .showSingleMediaType(true)// 是否只显示单一类型的文件
            .countable(true)// 数字选中标记
            .maxSelectable(max)// 最大选中数量
            .spanCount(4)// 列数
            .forResult(requestCode)
    }

    fun chooseVideo(activity: Activity, max: Int, requestCode: Int) {
        Matisse.from(activity)
            .choose(MimeType.ofVideo())// 设置选择的MIME类型
            .showSingleMediaType(true)// 是否只显示单一类型的文件
            .countable(true)// 数字选中标记
            .maxSelectable(max)// 最大选中数量
            .spanCount(4)// 列数
            .forResult(requestCode)
    }
}