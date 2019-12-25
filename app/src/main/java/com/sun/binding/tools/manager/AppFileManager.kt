package com.sun.binding.tools.manager

import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.Utils
import com.sun.binding.tools.ext.estimate
import java.io.File
import java.util.*

/**
 * 应用程序文件管理类
 */
@Suppress("unused")
object AppFileManager {

    /**
     * 获得图片保存目录
     */
    fun getAppPicturesDir(): String {
        return (Utils.getApp().getExternalFilesDir(DIRECTORY_PICTURES) ?: File(Utils.getApp().filesDir, DIRECTORY_PICTURES)).apply {
            if (!this.exists()) {
                this.mkdirs()
            }
        }.absolutePath
    }

    /**
     * 获得封面图片缓存目录
     */
    @JvmStatic
    fun getCoverCacheDir(): String {
        return (Utils.getApp().externalCacheDir ?: Utils.getApp().cacheDir).apply {
            if (!this.exists()) {
                this.mkdirs()
            }
        }.absolutePath + File.separator + "cover"
    }

    /**
     * 随机获得封面图片地址(jpg)
     */
    @JvmStatic
    fun getCoverCachePath(targetPath: String? = null) =
        getCoverCacheDir() +
                File.separator +
                targetPath.isNullOrBlank().estimate(UUID.randomUUID().toString(), EncryptUtils.encryptMD5ToString(targetPath)) +
                ".jpg"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun test() {
        // 内部存储
        val cacheDir = Utils.getApp().cacheDir //路径：/data/data/包名/cache
        val filesDir = Utils.getApp().filesDir //路径：/data/data/包名/files

        // 外部存储
        val externalCacheDir = Utils.getApp().externalCacheDir //路径：/sdcard/Android/data/包名/cache
        val externalFilesDir = Utils.getApp().getExternalFilesDir(DIRECTORY_PICTURES) //路径：/sdcard/Android/data/包名/files
        val mediaDirs = Utils.getApp().externalMediaDirs// 路径：/sdcard/Android/media/com.sun.binding

        // 外部存储(过时)
        Environment.getExternalStorageDirectory()
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    }
}