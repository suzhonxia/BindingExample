package com.sun.binding.tools.helper

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.constant.PermissionConstants.Permission
import com.blankj.utilcode.util.PermissionUtils

/**
 * 权限库封装
 */
object PermissionHelper {

    fun isGranted(vararg permissions: String?): Boolean = PermissionUtils.isGranted(*permissions)

    fun launchAppDetailsSettings() = PermissionUtils.launchAppDetailsSettings()

    fun requestPermission(context: Context, @Permission vararg permissions: String?, onGranted: (() -> Unit)?) {
        PermissionUtils.permission(*permissions)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: MutableList<String>?) {
                    onGranted?.invoke()
                }

                override fun onDenied(permissionsDeniedForever: MutableList<String>?, permissionsDenied: MutableList<String>?) {
                    MaterialDialog(context).show {
                        title(text = "提示")
                        message(text = "需要一些权限")
                        positiveButton(text = "去设置") { launchAppDetailsSettings() }
                        negativeButton(text = "取消") {}
                        lifecycleOwner()
                    }
                }
            })
            .request()
    }
}