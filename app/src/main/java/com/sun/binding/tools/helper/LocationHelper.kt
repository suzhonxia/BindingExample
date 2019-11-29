package com.sun.binding.tools.helper

import android.Manifest
import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.Utils

/**
 * 高得地图 SDK 封装
 */
class LocationHelper private constructor() {

    private object Single {
        val sin = LocationHelper()
    }

    companion object {
        fun getInstance() = Single.sin
    }

    private lateinit var locationClient: AMapLocationClient

    fun startLocation(context: Context, callback: (Double, Double) -> Unit) {
        createLocationClient(context).run {
            setLocationListener { aMapLocation -> callback.invoke(aMapLocation.latitude, aMapLocation.longitude) }
            startLocation()
        }
    }

    private fun createLocationClient(context: Context): AMapLocationClient {
        if (!this::locationClient.isInitialized) {
            locationClient = AMapLocationClient(context.applicationContext)
            locationClient.setLocationOption(createLocationOption())
        }
        return locationClient
    }

    private fun createLocationOption(): AMapLocationClientOption = AMapLocationClientOption().apply {
        locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
    }
}