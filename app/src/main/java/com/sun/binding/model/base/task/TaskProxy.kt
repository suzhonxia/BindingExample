package com.sun.binding.model.base.task

import android.content.Context
import com.sun.binding.entity.LocationEntity
import com.sun.binding.tools.helper.LocationHelper
import com.sun.binding.tools.manager.AppUserManager
import java.lang.IllegalStateException

/**
 * Task 代理实现类
 */
object TaskProxy {

    /**
     * 定位
     */
    fun startLocation(target: Any, context: Context, savable: Boolean = true, block: (() -> Unit)? = null) {
        if (target is LocationTarget) {
            LocationHelper.getInstance().startLocation(context) { latitude, longitude ->
                if (savable) {
                    AppUserManager.saveLocation(LocationEntity(latitude, longitude))
                }
                block?.invoke()
            }
        } else {
            throw IllegalStateException("Must to be the implementer of the LocationViewModel to call startLocation method")
        }
    }
}